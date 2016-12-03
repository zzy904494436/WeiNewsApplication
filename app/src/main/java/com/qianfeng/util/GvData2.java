package com.qianfeng.util;

import java.util.List;

/**
 * Created by Administrator on 2016/11/10.
 */

public class GvData2 {

    /**
     * status : true
     * count : 10
     * data : {"list":[{"Id":"43","title":"牛轰轰","img_src":"http://imgcdn.lerays.com/tag/2016/10/24/ff979045883be59a0abe5014b7403717.png!320","type":"1","subscribers":1136,"last_pubtime":"1478835184","o_time":"1477303820","is_subscribe":false},{"Id":"42","title":"拜托啦学妹","img_src":"http://imgcdn.lerays.com/tag/2016/09/28/3d55406924b99d9836687e8df4f96667.png!320","type":"1","subscribers":1054,"last_pubtime":"1478927313","o_time":"1475031937","is_subscribe":false},{"Id":"41","title":"奔波儿灞与灞波儿奔","img_src":"http://imgcdn.lerays.com/tag/2016/09/20/0ff64a1ad1fb77b3045899993b4c2741.png!320","type":"1","subscribers":978,"last_pubtime":"1479178368","o_time":"1474365747","is_subscribe":false},{"Id":"38","title":"猎奇恐怖漫画站","img_src":"http://imgcdn.lerays.com/tag/2016/09/18/df729f0c7acd4af478ac8fd16411e19d.jpg!320","type":"1","subscribers":665,"last_pubtime":"1478880014","o_time":"1474191301","is_subscribe":false},{"Id":"37","title":"爆漫画","img_src":"http://imgcdn.lerays.com/tag/2016/09/19/33ee4f5ab643a0997a79a27ec2964087.png!320","type":"1","subscribers":1184,"last_pubtime":"1479179401","o_time":"1474082890","is_subscribe":false},{"Id":"36","title":"大爱猫咪控","img_src":"http://imgcdn.lerays.com/tag/2016/09/14/762d97b9f1bd1195a33b90639ed4a17d.jpg!320","type":"1","subscribers":1074,"last_pubtime":"1479180432","o_time":"1473821451","is_subscribe":false},{"Id":"35","title":"小Chai的体育课堂","img_src":"http://imgcdn.lerays.com/tag/2016/09/14/af5f497b59b964fc71aa41bc067d0e4e.jpg!320","type":"1","subscribers":953,"last_pubtime":"1479006444","o_time":"1473671544","is_subscribe":false},{"Id":"34","title":"HUGO","img_src":"http://imgcdn.lerays.com/tag/2016/09/09/f86d6aff0f1e8d6efae5d574e9682fe7.jpg!320","type":"1","subscribers":862,"last_pubtime":"1479181805","o_time":"1473331166","is_subscribe":false},{"Id":"33","title":"黄小趣","img_src":"http://imgcdn.lerays.com/tag/2016/09/07/51db6fa37643c585f50ea92e454e7394.jpg!320","type":"1","subscribers":806,"last_pubtime":"1479181139","o_time":"1473231902","is_subscribe":false},{"Id":"32","title":"分分钟涨姿势","img_src":"http://imgcdn.lerays.com/tag/2016/09/06/b2310773508214eff6fa5a1423b0fa73.jpg!320","type":"1","subscribers":740,"last_pubtime":"1479181989","o_time":"1473153626","is_subscribe":false}],"nexttime":"1473153626","nextsign":"d66323cf809452db70c139fd835fbb43"}
     * ext : {"c_time":1479194469}
     */

    private boolean status;
    private int count;
    private DataBean data;
    private ExtBean ext;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public ExtBean getExt() {
        return ext;
    }

    public void setExt(ExtBean ext) {
        this.ext = ext;
    }

    public static class DataBean {
        /**
         * list : [{"Id":"43","title":"牛轰轰","img_src":"http://imgcdn.lerays.com/tag/2016/10/24/ff979045883be59a0abe5014b7403717.png!320","type":"1","subscribers":1136,"last_pubtime":"1478835184","o_time":"1477303820","is_subscribe":false},{"Id":"42","title":"拜托啦学妹","img_src":"http://imgcdn.lerays.com/tag/2016/09/28/3d55406924b99d9836687e8df4f96667.png!320","type":"1","subscribers":1054,"last_pubtime":"1478927313","o_time":"1475031937","is_subscribe":false},{"Id":"41","title":"奔波儿灞与灞波儿奔","img_src":"http://imgcdn.lerays.com/tag/2016/09/20/0ff64a1ad1fb77b3045899993b4c2741.png!320","type":"1","subscribers":978,"last_pubtime":"1479178368","o_time":"1474365747","is_subscribe":false},{"Id":"38","title":"猎奇恐怖漫画站","img_src":"http://imgcdn.lerays.com/tag/2016/09/18/df729f0c7acd4af478ac8fd16411e19d.jpg!320","type":"1","subscribers":665,"last_pubtime":"1478880014","o_time":"1474191301","is_subscribe":false},{"Id":"37","title":"爆漫画","img_src":"http://imgcdn.lerays.com/tag/2016/09/19/33ee4f5ab643a0997a79a27ec2964087.png!320","type":"1","subscribers":1184,"last_pubtime":"1479179401","o_time":"1474082890","is_subscribe":false},{"Id":"36","title":"大爱猫咪控","img_src":"http://imgcdn.lerays.com/tag/2016/09/14/762d97b9f1bd1195a33b90639ed4a17d.jpg!320","type":"1","subscribers":1074,"last_pubtime":"1479180432","o_time":"1473821451","is_subscribe":false},{"Id":"35","title":"小Chai的体育课堂","img_src":"http://imgcdn.lerays.com/tag/2016/09/14/af5f497b59b964fc71aa41bc067d0e4e.jpg!320","type":"1","subscribers":953,"last_pubtime":"1479006444","o_time":"1473671544","is_subscribe":false},{"Id":"34","title":"HUGO","img_src":"http://imgcdn.lerays.com/tag/2016/09/09/f86d6aff0f1e8d6efae5d574e9682fe7.jpg!320","type":"1","subscribers":862,"last_pubtime":"1479181805","o_time":"1473331166","is_subscribe":false},{"Id":"33","title":"黄小趣","img_src":"http://imgcdn.lerays.com/tag/2016/09/07/51db6fa37643c585f50ea92e454e7394.jpg!320","type":"1","subscribers":806,"last_pubtime":"1479181139","o_time":"1473231902","is_subscribe":false},{"Id":"32","title":"分分钟涨姿势","img_src":"http://imgcdn.lerays.com/tag/2016/09/06/b2310773508214eff6fa5a1423b0fa73.jpg!320","type":"1","subscribers":740,"last_pubtime":"1479181989","o_time":"1473153626","is_subscribe":false}]
         * nexttime : 1473153626
         * nextsign : d66323cf809452db70c139fd835fbb43
         */

        private String nexttime;
        private String nextsign;
        private List<ListBean> list;

        public String getNexttime() {
            return nexttime;
        }

        public void setNexttime(String nexttime) {
            this.nexttime = nexttime;
        }

        public String getNextsign() {
            return nextsign;
        }

        public void setNextsign(String nextsign) {
            this.nextsign = nextsign;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * Id : 43
             * title : 牛轰轰
             * img_src : http://imgcdn.lerays.com/tag/2016/10/24/ff979045883be59a0abe5014b7403717.png!320
             * type : 1
             * subscribers : 1136
             * last_pubtime : 1478835184
             * o_time : 1477303820
             * is_subscribe : false
             */

            private String Id;
            private String title;
            private String img_src;
            private String type;
            private int subscribers;
            private String last_pubtime;
            private String o_time;
            private boolean is_subscribe;

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg_src() {
                return img_src;
            }

            public void setImg_src(String img_src) {
                this.img_src = img_src;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getSubscribers() {
                return subscribers;
            }

            public void setSubscribers(int subscribers) {
                this.subscribers = subscribers;
            }

            public String getLast_pubtime() {
                return last_pubtime;
            }

            public void setLast_pubtime(String last_pubtime) {
                this.last_pubtime = last_pubtime;
            }

            public String getO_time() {
                return o_time;
            }

            public void setO_time(String o_time) {
                this.o_time = o_time;
            }

            public boolean isIs_subscribe() {
                return is_subscribe;
            }

            public void setIs_subscribe(boolean is_subscribe) {
                this.is_subscribe = is_subscribe;
            }
        }
    }

    public static class ExtBean {
        /**
         * c_time : 1479194469
         */

        private int c_time;

        public int getC_time() {
            return c_time;
        }

        public void setC_time(int c_time) {
            this.c_time = c_time;
        }
    }
}
