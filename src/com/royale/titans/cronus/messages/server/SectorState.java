package com.royale.titans.cronus.messages.server;

import com.royale.titans.cronus.Utils;
import com.royale.titans.cronus.lib.Buffer;
import com.royale.titans.cronus.messages.ServerMessage;

public class SectorState extends ServerMessage  {
    @Override
    public int getId() {
        return 21873;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public Buffer getBuffer() {
        return Buffer.wrap(Utils.h2b("018d030000789c9552cf6b1341147e6f76df26cd6637426b6cd3d436262d25a287a022e265d35d24d02aa651f114526da197140a5ea49086d234626c4d14b5a8072568add5a278482e2a8878f054d0b3bfff01ef8a33bbf9e14114bf9d79bbfbbd6f1edfcc1b8c32868c2dcac0d19d4a98e951c334ad64faf8096b3c958e9d8ca54f258e8a2421222003f9baaddd2eb42306d738caa4914ca44e8bcc613e514c2e8f6cfc5853db81b34a7632337b66462b19657a18ba108fc28aa58bb06e81eeee242c7d7711bb1c22492619c85b0f9306a47792af1c25ff9521eab9f7a6be8514705310a8af507c6f520868175018280234043468db2565dd22d7e604a961eaf250afce0203252510eb05f0b28e07b7252ef28d1c1b4f1963712339608d26525689ad14385fad60fd1a32b7a8c3b4fcab77ae7610d4913d5ad928d28bc14d73186e0afb0573be61bf9025f6dc4f9264bb7fb2d3768fe4bbbf83fc97faa9e7d99da78b3205e47fbaaf73f717fda4f6539797bbc7a02f8fc27a5ff563842bf478666a6ada18cb64cf4dcc4ecfacb21b0b1882c707efeecfe9a202ec8650e553ed91aa42f2eda10f5bcbb5055507cd694373270e78a3a0f9173ccb5b745e0963e38130ef25cf23f10b408cf8b68854adfa196f2df393cac1cb397e20ce5a55db38509d14e4bcec5c8106f99bb4c5f20a6d6dabc05a096b7b9bac836e7e1af647ae8196b2f8ba55d6414076e5e6988c12d96a9efbc3d2ff45655f7bd492620854f1eff1a7e45196b06315d955a46d14a4614f1edd400a633243a9559d2178f9ebdb97a5af1ef80518c0a07f"));
    }
}
