package com.royale.titans.cronus.lib;

import com.royale.titans.cronus.ServerLogic;
import com.royale.titans.cronus.models.BattleInfo;

public class BattleChecksumEncoder {
    private static final int[] WEED_TABLE = new int[] {
            0x431bde83, 0x3d09, 0x154ac8ce, 0xde68b267
    };

    private int mChecksum = 0;
    private final BattleInfo mBattleInfo;

    public BattleChecksumEncoder(BattleInfo battleInfo) {
        mBattleInfo = battleInfo;
    }

    private int __ror4__(int x) {
        long e = (long) Math.pow(2, 32);
        return (int) (((x & (e - 1)) >> 31 % 32) | (x << (32 - (31 % 32)) & (e - 1)));
    }

    private int sc_ror(int x, int a) {
        return ((x + __ror4__(a)) + 0x21);
    }

    private int sc_x(int x) {
        int a = (int) ((((long) WEED_TABLE[0]) * x & 0xffffffff00000000L) >> 32);
        a = (a >> 18) + (a >> 31);
        return x - ((a * WEED_TABLE[1]) << 6) + 1;
    }

    private int sc_card_x(int x) {
        int a = sc_x(x);
        if (x >= 28000000) {
            a += 77;
        } else if (x >= 27000000) {
            a += 63;
        }
        return a;
    }

    private void updateChecksum(int value) {
        mChecksum = sc_ror(value, mChecksum);
    }

    private void updateChecksumX(int value) {
        value = sc_x(value);
        updateChecksum(value);
    }

    private void updateChecksumH(int value) {
        value = value > 0 ? 13 : 7;
        mChecksum = value + __ror4__(mChecksum);
    }

    public int encode(ServerLogic.ClientInfo clientInfo) {
        // reset checksum, always start from magic
        mChecksum = 0;

        int sequence = mBattleInfo.getSequence();
        int x;
        int magic = sequence * 10;

        // start from magic
        updateChecksum(magic);
        // self update
        updateChecksum(mChecksum);
        // timestamp in minutes
        updateChecksum((int) (mBattleInfo.getGameStartTimestamp() / 1000));
        // hardcoded
        updateChecksum(0xb);
        // magic again
        updateChecksum(magic);

        // this is shit
        mChecksum = WEED_TABLE[2] + __ror4__(mChecksum) + 0x9;
        updateChecksum(WEED_TABLE[3]);

        // todo get all values from sectorstate
        // unk
        updateChecksumX(15000029);

        updateChecksum(0);

        // arena
        updateChecksumX(54000024);

        // opponents
        for (ServerLogic.ClientInfo player : mBattleInfo.getPlayers()) {
            if (!player.getClientId().equals(clientInfo.getClientId())) {
                updateChecksum(player.getClientId().high());
                updateChecksum(player.getClientId().low());
            }
        }

        updateChecksum(0);

        // own account id
        updateChecksum(clientInfo.getClientId().high());
        updateChecksum(clientInfo.getClientId().low());

        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);

        updateChecksum(4);
        updateChecksum(1);
        updateChecksum(0);
        updateChecksum(0);

        updateChecksumX(72000007);

        updateChecksum(0);
        updateChecksum(0);

        updateChecksumH(0);
        updateChecksumH(0);
        updateChecksumH(0);
        updateChecksumH(0);
        updateChecksumH(0);
        updateChecksumH(0);
        updateChecksumH(0);

        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);

        updateChecksum(116);
        updateChecksum(0 - 116);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(6);
        updateChecksum(0 - 6);
        updateChecksum(6);

        for (int i=0;i<4;i++) {
            updateChecksumX(35000001);
            mChecksum += 0x21;
            updateChecksum(1);
        }

        for (int i=0;i<2;i++) {
            updateChecksumX(35000000);
            mChecksum += 0x22;
            updateChecksum(0);
        }

        updateChecksum(1);
        updateChecksum(0);
        updateChecksum(1);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(1);

        for (int i=0;i<6;i++) {
            updateChecksumX(5000000 + i);
            mChecksum += 2;
            if (i == 1) {
                mChecksum += 9;
            } else if (i == 3) {
                mChecksum -= 1;
            } else if (i == 4) {
                mChecksum -= 2;
            } else if (i == 5) {
                mChecksum -= 3;
            }
            updateChecksum(i);
        }

        // towers
        updateChecksum(8);
        updateChecksum(13);
        updateChecksum(14500);
        updateChecksum(25500);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0xffffffff);
        updateChecksum(0);
        updateChecksum(0xffffff00);
        updateChecksum(500 * sequence);
        updateChecksum(0);
        updateChecksum(2);
        updateChecksum(0);
        updateChecksum(0);

        updateChecksum(0);
        updateChecksum(0);
        updateChecksumH(0);
        updateChecksumH(0);
        updateChecksum(0);
        updateChecksumH(0);

        updateChecksum(8);
        updateChecksum(13);
        updateChecksum(3500);
        updateChecksum(6500);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0xffffffff);
        updateChecksum(0);
        updateChecksum(256);
        updateChecksum(500 * sequence);
        updateChecksum(0);
        updateChecksum(1);
        updateChecksum(0);
        updateChecksum(0);

        updateChecksum(0);
        updateChecksum(0);
        updateChecksumH(0);
        updateChecksumH(0);
        updateChecksum(0);
        updateChecksumH(0);

        updateChecksum(8);
        updateChecksum(13);
        updateChecksum(3500);
        updateChecksum(25500);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0xffffffff);
        updateChecksum(0);
        updateChecksum(0xffffff00);
        updateChecksum(500 * sequence);
        updateChecksum(0);
        updateChecksum(1);
        updateChecksum(0);
        updateChecksum(0);

        updateChecksum(0);
        updateChecksum(0);
        updateChecksumH(0);
        updateChecksumH(0);
        updateChecksum(0);
        updateChecksumH(0);

        updateChecksum(8);
        updateChecksum(13);
        updateChecksum(14500);
        updateChecksum(6500);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0xffffffff);
        updateChecksum(0);
        updateChecksum(256);
        updateChecksum(500 * sequence);
        updateChecksum(0);
        updateChecksum(2);
        updateChecksum(0);
        updateChecksum(0);

        updateChecksum(0);
        updateChecksum(0);
        updateChecksumH(0);
        updateChecksumH(0);
        updateChecksum(0);
        updateChecksumH(0);

        // king towers
        updateChecksum(8);
        updateChecksum(13 - 1);
        updateChecksum(9000);
        updateChecksum(3000);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0xffffffff);
        updateChecksum(0);
        updateChecksum(256);
        updateChecksum(500 * sequence);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);

        updateChecksum(0);
        updateChecksum(0);
        updateChecksumH(0);
        updateChecksumH(0);
        updateChecksum(0);
        updateChecksumH(0);
        updateChecksumH(0);
        updateChecksumH(0);
        updateChecksumH(1);
        updateChecksumH(1);

        updateChecksum((50000 * sequence) % 280000);
        updateChecksum(0);
        updateChecksum((int) (5 + (sequence >= 5 ? 0 : Math.floor(sequence / 6))));
        if (sequence > 5) {
            mChecksum += Math.floor(sequence / 6);
        }
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0xffffffff);
        updateChecksum(0xffffffff);
        updateChecksum(0xffffffff);
        updateChecksum(0xffffffff);
        updateChecksum(0xffffffff);
        updateChecksum(0xffffffff);
        updateChecksum(0xffffffff);
        updateChecksum(0xffffffff);

        updateChecksumH(0);
        updateChecksumH(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);

        updateChecksum(8);
        updateChecksum(13 - 1);
        updateChecksum(9000);
        updateChecksum(29000);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0xffffffff);
        updateChecksum(0);
        updateChecksum(0xffffff00);
        updateChecksum(500 * sequence);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);

        updateChecksum(0);
        updateChecksum(0);
        updateChecksumH(0);
        updateChecksumH(0);
        updateChecksum(0);
        updateChecksumH(0);
        updateChecksumH(1);
        updateChecksumH(0);
        updateChecksumH(1);
        updateChecksumH(1);

        updateChecksum(4);
        updateChecksum(3);
        updateChecksum(4);
        updateChecksum(0xfffffffa);
        updateChecksum(5);
        updateChecksum(4);
        updateChecksum(5);
        updateChecksum(2);
        updateChecksum(0);
        updateChecksum(4);
        updateChecksum(0);
        updateChecksum(0xffffffff);
        updateChecksum(0xffffffff);
        updateChecksum(0xffffffff);
        updateChecksum(0);
        updateChecksum(0xffffffff);

        updateChecksum((50000 * sequence) % 280000);
        updateChecksum(0);
        updateChecksum((int) (5 + (sequence >= 5 ? 0 : Math.floor(sequence / 6))));
        if (sequence > 5) {
            mChecksum += Math.floor(sequence / 6);
        }
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0xffffffff);
        updateChecksum(0xffffffff);
        updateChecksum(0xffffffff);
        updateChecksum(0xffffffff);
        updateChecksum(0xffffffff);
        updateChecksum(0xffffffff);
        updateChecksum(0xffffffff);
        updateChecksum(0xffffffff);

        updateChecksumH(0);
        updateChecksumH(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksumH(0);
        updateChecksumH(0);
        updateChecksum(0);

        // some towers values again in reverse order
        // t4
        updateChecksum(14500);
        updateChecksum(6500);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        x = sc_x(5000003) + 1;
        updateChecksum(x);
        updateChecksum(3);
        updateChecksumH(0);
        updateChecksumH(0);
        updateChecksum(0);

        // t3
        updateChecksum(3500);
        updateChecksum(25500);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        x = sc_x(5000002) + 2;
        updateChecksum(x);
        updateChecksum(2);
        updateChecksumH(0);
        updateChecksumH(0);
        updateChecksum(0);

        // t2
        updateChecksum(3500);
        updateChecksum(6500);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        x = sc_x(5000001) + 3;
        updateChecksum(x);
        updateChecksum(1);
        updateChecksumH(0);
        updateChecksumH(0);
        updateChecksum(0);

        // t1
        updateChecksum(14500);
        updateChecksum(25500);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        x = sc_x(5000000) + 4;
        updateChecksum(x);
        updateChecksum(0);
        updateChecksumH(0);
        updateChecksumH(0);
        updateChecksum(0);

        // king towers again in reverse order
        // k2
        updateChecksum(9000);
        updateChecksum(29000);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksumX(5000004);
        updateChecksum(5);
        updateChecksumH(0);
        updateChecksumH(0);
        updateChecksum(0);

        // k1
        updateChecksum(9000);
        updateChecksum(3000);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksumX(5000004);
        updateChecksum(4);

        // other things from sector state
        updateChecksum(2534);
        updateChecksum(100);
        updateChecksum(0);
        updateChecksum(2534);
        updateChecksum(100);
        updateChecksum(0);
        updateChecksum(2534);
        updateChecksum(100);
        updateChecksum(0);
        updateChecksum(2534);
        updateChecksum(100);
        updateChecksum(0);
        updateChecksum(4008);
        updateChecksum(100);
        updateChecksum(0);
        updateChecksum(4008);
        updateChecksum(100);
        updateChecksum(0);

        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(100);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(100);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(100);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(100);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(100);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(100);
        updateChecksum(0);

        updateChecksumH(0);
        updateChecksumH(1);
        updateChecksumH(1);
        updateChecksumH(1);
        updateChecksumH(1);
        updateChecksumH(1);
        updateChecksumH(1);
        updateChecksumH(1);
        updateChecksumH(1);
        updateChecksumH(1);

        // cards
        // 1
        x = sc_card_x(clientInfo.getCurrentDeck().get(0).getScId());
        updateChecksum(x);
        updateChecksum(clientInfo.getCurrentDeck().get(0).getMaxLevel());
        // 2
        x = sc_card_x(clientInfo.getCurrentDeck().get(1).getScId());
        updateChecksum(x);
        updateChecksum(clientInfo.getCurrentDeck().get(1).getMaxLevel());
        // 3
        x = sc_card_x(clientInfo.getCurrentDeck().get(2).getScId());
        updateChecksum(x);
        updateChecksum(clientInfo.getCurrentDeck().get(2).getMaxLevel());
        // 4
        x = sc_card_x(clientInfo.getCurrentDeck().get(3).getScId());
        updateChecksum(x);
        updateChecksum(clientInfo.getCurrentDeck().get(3).getMaxLevel());
        // 5
        x = sc_card_x(clientInfo.getCurrentDeck().get(4).getScId());
        updateChecksum(x);
        updateChecksum(clientInfo.getCurrentDeck().get(4).getMaxLevel());
        // 6
        x = sc_card_x(clientInfo.getCurrentDeck().get(5).getScId());
        updateChecksum(x);
        updateChecksum(clientInfo.getCurrentDeck().get(5).getMaxLevel());
        // 7
        x = sc_card_x(clientInfo.getCurrentDeck().get(6).getScId());
        updateChecksum(x);
        updateChecksum(clientInfo.getCurrentDeck().get(6).getMaxLevel());
        // 8
        x = sc_card_x(clientInfo.getCurrentDeck().get(7).getScId());
        updateChecksum(x);
        updateChecksum(clientInfo.getCurrentDeck().get(7).getMaxLevel());

        // other stuffs
        updateChecksum(0);
        // another shit
        x = sc_x(5000004);
        updateChecksum(x);
        mChecksum -= x;

        x = sc_x(5000005) - 1;
        updateChecksum(x);

        updateChecksumX(5000005);

        updateChecksum(2);
        updateChecksumX(5000001);
        updateChecksumX(5000003);
        updateChecksum(2);
        updateChecksumX(5000000);
        updateChecksumX(5000002);

        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);

        updateChecksumH(0);
        updateChecksum(0);

        updateChecksum(0);
        updateChecksum(4);
        updateChecksum(1);
        updateChecksum(5);
        updateChecksum(4);
        updateChecksum(3);
        updateChecksum(12);
        updateChecksum(0);
        updateChecksum(0);
        updateChecksum(0);

        return mChecksum;
    }
}
