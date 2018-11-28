package com.ladingwu.imageloaderframework;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wuzhao on 2018/3/3.
 */

public class DataUrls {
    private String[] urls={
            "https://static-ali.ihansen.org/app/bg1440/5LOhydOtTKU.jpg?x-oss-process=style/w400",
            "https://static-ali.ihansen.org/app/bg1440/w6Lb5Px8a6g.jpg?x-oss-process=style/w400",
            "https://static-ali.ihansen.org/app/bg1440/vqKnuG8GaQc.jpg?x-oss-process=style/w400",
            "https://static-ali.ihansen.org/app/bg1440/STiVSlutjt8.jpg?x-oss-process=style/w400",
            "https://static-ali.ihansen.org/app/bg1440/pJqfhKUpCh8.jpg",
            "https://static-ali.ihansen.org/app/bg1440/zR5DV_6wOms.jpg",
            "https://static-ali.ihansen.org/app/bg1440/xD-zW8wUOnI.jpg?x-oss-process=style/w400",
            "http://f0.topitme.com/0/7a/63/113144393585b637a0o.jpg",
            "http://pic.58pic.com/58pic/15/14/14/18e58PICMwt_1024.jpg",
            "http://image.tianjimedia.com/uploadImages/2015/215/41/M68709LC8O6L.jpg",
            "http://fb.topitme.com/b/3b/1a/11266050750a71a3bbo.jpg",
            "http://a3.topitme.com/1/21/79/1128833621e7779211o.jpg",
            "http://f2.topitme.com/2/97/2a/11267742687302a972o.jpg",
            "http://www.zhlzw.com/UploadFiles/Article_UploadFiles/201204/20120412123913661.jpg",
            "http://pic9.nipic.com/20100915/2531170_123841801039_2.jpg",
            "http://pic.58pic.com/58pic/13/96/73/63H58PICzwQ_1024.jpg",
            "http://www.zhlzw.com/UploadFiles/Article_UploadFiles/201303/2013030914472860.jpg",
            "http://a0.att.hudong.com/31/35/300533991095135084358827466.jpg",
            "http://pic.58pic.com/58pic/15/24/50/43Q58PICkj4_1024.jpg",
            "http://pic18.nipic.com/20111206/2256974_132005956000_2.jpg",
            "http://a3.topitme.com/f/d1/4b/11292760524e84bd1fo.jpg",
            "http://img.taopic.com/uploads/allimg/140806/235020-140P60H10661.jpg",
            "http://pic9.nipic.com/20100919/5123760_093408576078_2.jpg",
            "http://img1.imgtn.bdimg.com/it/u=2676848174,1566324329&fm=27&gp=0.jpg",
            "http://img.taopic.com/uploads/allimg/140903/318755-140Z30U02921.jpg",
            "http://pic35.photophoto.cn/20150529/0036036806461415_b.jpg",
            "http://a3.topitme.com/5/39/23/11280130996ee23395o.jpg",
            "http://pic30.nipic.com/20130624/2929309_161747269100_2.jpg",
            "http://pic18.nipic.com/20120110/9144829_015426360148_2.jpg",
            "http://pic15.nipic.com/20110613/114893_120440350166_2.jpg",
            "http://www.zhlzw.com/UploadFiles/Article_UploadFiles/201210/2012102917584177.jpg",
            "http://pic28.photophoto.cn/20130731/0036036821137272_b.jpg",
            "http://pic31.nipic.com/20130728/7447430_145214729000_2.jpg",
            "http://pic7.nipic.com/20100607/2177138_154346307121_2.jpg",
            "http://f7.topitme.com/7/d4/ca/113104798380acad47o.jpg",
            "http://pic18.photophoto.cn/20110304/0036036886815124_b.jpg",
            "http://img.taopic.com/uploads/allimg/140613/240466-1406130QP479.jpg",
            "http://img.taopic.com/uploads/allimg/120625/201819-120625232Z898.jpg",
            "http://pic25.photophoto.cn/20121117/0036036800515046_b.jpg",
            "http://pic19.nipic.com/20120310/3145425_112535345000_2.jpg",
            "http://pic.qiantucdn.com/58pic/13/77/15/29n58PICV6A_1024.jpg",
            "http://img.taopic.com/uploads/allimg/140817/235032-140QF92H850.jpg",
            "http://img01.taopic.com/140921/318765-1409210H95759.jpg",
            "http://pic.58pic.com/58pic/13/79/56/53M58PICkvQ_1024.jpg",
            "http://pic7.nipic.com/20100609/3017209_215405863383_2.jpg",
            "http://www.zhlzw.com/UploadFiles/Article_UploadFiles/201210/2012102917591370.jpg",
            "http://pic8.nipic.com/20100802/5191055_103315277065_2.jpg",
            "http://pic28.nipic.com/20130402/4021224_162709472124_2.jpg",
            "http://pic.58pic.com/58pic/14/26/06/26c58PICHg6_1024.jpg",
            "http://pic.58pic.com/58pic/13/76/82/58PIC3p58PICGBQ_1024.jpg",
            "http://pic30.nipic.com/20130624/7447430_170550396000_2.jpg",
            "http://img4.imgtn.bdimg.com/it/u=566738342,979573310&fm=27&gp=0.jpg",
            "http://a3.topitme.com/9/bd/a6/1128623636e63a6bd9o.jpg",
            "http://pic15.nipic.com/20110621/2786001_090001397000_2.jpg",
            "http://pic15.nipic.com/20110621/2786001_090001487000_2.jpg",
            "http://img2.imgtn.bdimg.com/it/u=1425206197,1352610685&fm=27&gp=0.jpg",
            "http://dynamic-image.yesky.com/740x-/uploadImages/2014/289/01/IGS09651F94M.jpg",
            "http://a3.topitme.com/1/21/79/1128833621e7779211o.jpg",
            "http://a3.topitme.com/1/29/7e/1128366476bf37e291o.jpg",
            "http://imgsrc.baidu.com/image/c0%3Dpixel_huitu%2C0%2C0%2C294%2C40/sign=ecfe83b9042442a7ba03f5e5b83bc827/728da9773912b31bc2fe74138d18367adab4e17e.jpg",
            "http://pic.yesky.com/uploadImages/2015/215/45/04L5VRR21C5W.jpg",
            "http://dynamic-image.yesky.com/740x-/uploadImages/2015/131/33/D472XQ25C7H2.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/2fdda3cc7cd98d10eb53bc0c2a3fb80e7bec900c.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/2fdda3cc7cd98d10eb53bc0c2a3fb80e7bec900c.jpg",
            "http://pic1.win4000.com/wallpaper/2017-12-19/5a387cb8439ea.jpg",
            "http://pic1.win4000.com/wallpaper/2017-12-19/5a387cb8439ea.jpg",
            "http://pic1.win4000.com/wallpaper/2017-12-19/5a387cfd18684.jpg",
            "http://pic1.win4000.com/wallpaper/2017-12-19/5a387ce416877.jpg",
            "http://pic1.win4000.com/wallpaper/2017-10-14/59e1bb9f01314.jpg",
            "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1309/05/c3/25274577_1378321466195.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/dbb44aed2e738bd41f16dd0daa8b87d6277ff9d3.jpg",
            "http://scimg.jb51.net/allimg/170116/106-1F116114312L2.jpg",
            "http://pic1.win4000.com/wallpaper/2017-10-14/59e1bbd4dafcb.jpg",
            "hhttp://pic13.nipic.com/20110414/3970232_185648319112_2.jpg",
            "http://pic.58pic.com/58pic/14/27/40/58PIC6d58PICy68_1024.jpg"

    };


    public List<String>  getUrls(){
        return Arrays.asList(urls);
    }
}
