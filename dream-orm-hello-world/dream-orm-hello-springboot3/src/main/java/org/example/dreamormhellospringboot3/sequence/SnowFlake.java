package org.example.dreamormhellospringboot3.sequence;

public class SnowFlake {
    /**
     * 锁对象
     */
    protected final static byte[] _SyncLock = new byte[0];
    /**
     * 基础时间
     */
    protected final long baseTime;
    /**
     * 机器码
     */
    protected final short workerId;
    /**
     * 机器码位长
     */
    protected final byte workerIdBitLength;
    /**
     * 数据中心id
     */
    protected final short dataCenterId;
    /**
     * 数据中心id位长
     */
    protected final byte dataCenterIdBitLength;
    /**
     * 自增序列数位长
     */
    protected final byte seqBitLength;
    /**
     * 最大序列数（含）
     */
    protected final int maxSeqNumber;
    /**
     * 最小序列数（含）
     */
    protected final short minSeqNumber;
    /**
     * 最大漂移次数（含）
     */
    protected final int topOverCostCount;
    /**
     * 时间戳位移位
     */
    protected final byte timestampShift;
    /**
     * 数据中心id位移
     */
    protected final byte dataCenterShift;
    /**
     * 当前能使用的序列数id
     */
    protected short currentSeqNumber;
    /**
     * 最后一次生成id的时间戳差值
     */
    protected long lastTimeTick = 0;
    /**
     * 回拨时间戳差值
     */
    protected long turnBackTimeTick = 0;
    /**
     * 回拨序数位索引
     */
    protected byte turnBackIndex = 0;
    /**
     * 超出当前毫秒序号最大长度标识
     */
    protected boolean overCost = false;
    protected int overCostCountInOneTerm = 0;
    protected int genCountInOneTerm = 0;
    /**
     * 序列数索引，用一个+1，【0,4】预留
     */
    protected int termIndex = 0;

    public SnowFlake() {
        this(1672502400000l);
    }

    public SnowFlake(long baseTime) {
        this(baseTime, (byte) 6);
    }

    public SnowFlake(long baseTime, byte seqBitLength) {
        this(baseTime, (byte) 0, (short) 0, (byte) 0, (short) 0, seqBitLength);
    }

    /**
     * 构造参数准备
     */
    public SnowFlake(long baseTime, byte workerIdBitLength, short workerId, byte dataCenterIdBitLength, short dataCenterId, byte seqBitLength) {
        this.baseTime = baseTime;
        this.workerIdBitLength = workerIdBitLength;
        this.workerId = workerId;
        this.seqBitLength = seqBitLength;
        this.dataCenterId = dataCenterId;
        minSeqNumber = 5;
        this.dataCenterIdBitLength = dataCenterIdBitLength;
        maxSeqNumber = (1 << seqBitLength) - 1;
        topOverCostCount = 2000;
        //时间戳位移为 机器码位长 + 数据中心id位长 + 序数号位长
        timestampShift = (byte) (workerIdBitLength + dataCenterIdBitLength + seqBitLength);
        //数据中心id位移为 机器码位长 + 数据中心id位长 + 序数号位长
        dataCenterShift = (byte) (dataCenterIdBitLength + seqBitLength);
        currentSeqNumber = minSeqNumber;
    }

    private void endOverCostAction(long useTimeTick) {
        if (termIndex > 10000) {
            termIndex = 0;
        }
    }

    /**
     * 正常的获取下一个id，生成id核心代码
     *
     * @return 下一个id
     */
    private long NextNormalId() {
        long currentTimeTick = getCurrentTimeTick();

        //如果出现时间回拨
        if (currentTimeTick < lastTimeTick) {
            if (turnBackTimeTick < 1) {
                turnBackTimeTick = lastTimeTick - 1;
                turnBackIndex++;

                // 每毫秒序列数的前5位是预留位，0用于手工新值，1-4是时间回拨次序
                // 支持4次回拨次序（避免回拨重叠导致ID重复），可无限次回拨（次序循环使用）。
                if (turnBackIndex > 4) {
                    turnBackIndex = 1;
                }
            }

            return calcTurnBackId(turnBackTimeTick);
        }

        // 时间追平时，_TurnBackTimeTick清零
        if (turnBackTimeTick > 0) {
            turnBackTimeTick = 0;
        }

        if (currentTimeTick > lastTimeTick) {
            lastTimeTick = currentTimeTick;
            currentSeqNumber = minSeqNumber;

            return calcId(lastTimeTick);
        }

        //当前序列数
        if (currentSeqNumber > maxSeqNumber) {
            termIndex++;
            lastTimeTick++;
            currentSeqNumber = minSeqNumber;
            overCost = true;
            overCostCountInOneTerm = 1;
            genCountInOneTerm = 1;

            return calcId(lastTimeTick);
        }

        return calcId(lastTimeTick);
    }

    /**
     * 超出该毫秒内的支持的生成数，生成id
     *
     * @return long生产的id
     */
    private long nextOverCostId() {
        long currentTimeTick = getCurrentTimeTick();

        //如果出现时间回拨
        if (currentTimeTick > lastTimeTick) {
            endOverCostAction(currentTimeTick);

            lastTimeTick = currentTimeTick;
            currentSeqNumber = minSeqNumber;
            overCost = false;
            overCostCountInOneTerm = 0;
            genCountInOneTerm = 0;

            return calcId(lastTimeTick);
        }


        if (overCostCountInOneTerm >= topOverCostCount) {
            endOverCostAction(currentTimeTick);

            lastTimeTick = getNextTimeTick();
            currentSeqNumber = minSeqNumber;
            overCost = false;
            overCostCountInOneTerm = 0;
            genCountInOneTerm = 0;

            return calcId(lastTimeTick);
        }

        if (currentSeqNumber > maxSeqNumber) {
            lastTimeTick++;
            currentSeqNumber = minSeqNumber;
            overCost = true;
            overCostCountInOneTerm++;
            genCountInOneTerm++;

            return calcId(lastTimeTick);
        }

        genCountInOneTerm++;
        return calcId(lastTimeTick);
    }

    /**
     * 正常情况下，采用左位移拼接结果id
     *
     * @param useTimeTick 时间戳差值
     * @return 生成的id
     */
    private long calcId(long useTimeTick) {
        long result = shiftStitchingResult(useTimeTick);
        currentSeqNumber++;
        return result;
    }

    /**
     * 发生时间回拨的时候，采用左位移拼接结果id
     *
     * @param useTimeTick 时间戳差值
     * @return 生成的id
     */
    private long calcTurnBackId(long useTimeTick) {
        long result = shiftStitchingResult(useTimeTick);
        turnBackTimeTick--;
        return result;
    }

    /**
     * 左位移拼接返回的id
     *
     * @param useTimeTick 时间差值
     * @return 生成的id
     */
    protected long shiftStitchingResult(long useTimeTick) {
        /**
         采用BigInteger重构，但是并发量可能会低，需要测试
         return BigInteger.valueOf(useTimeTick)
         .shiftLeft(timestampShift).add(BigInteger.valueOf(dataCenterId))
         .shiftLeft(dataCenterShift).add(BigInteger.valueOf(workerId))
         .shiftLeft(seqBitLength).add(BigInteger.valueOf(currentSeqNumber));
         **/

        return ((useTimeTick << timestampShift) + //时间差值，时间戳位移 = 数据中心id位长 + 机器码位长 + 序数位长
                ((long) dataCenterId << dataCenterShift) + //数据中心id，数据中心id位移 = 机器码位长 + 序数位长
                ((long) workerId << seqBitLength) + //机器码数，机器码位移 = 序数位长
                (int) currentSeqNumber);
    }

    /**
     * 获取当前时间 - 系统时间差值
     *
     * @return 时间差值
     */
    protected long getCurrentTimeTick() {
        long millis = System.currentTimeMillis();
        return millis - baseTime;
    }

    /**
     * 获取下次时间差值
     *
     * @return 时间差值
     */
    protected long getNextTimeTick() {
        long tempTimeTicker = getCurrentTimeTick();
        while (tempTimeTicker <= lastTimeTick) {
            try {
                Thread.sleep(10); //发生回拨等待3毫秒，实际上是阻塞10毫秒生成
            } catch (InterruptedException e) {
                throw new RuntimeException("Error when time callback waits three millisecond");
            }
            tempTimeTicker = getCurrentTimeTick();
        }

        return tempTimeTicker;
    }

    public long next() {
        synchronized (_SyncLock) {
            return overCost ? nextOverCostId() : NextNormalId();
        }
    }
}
