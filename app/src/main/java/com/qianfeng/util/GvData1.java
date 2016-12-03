package com.qianfeng.util;

import java.util.List;

/**
 * Created by Administrator on 2016/11/10.
 */

public class GvData1 {

    /**
     * status : true
     * count : -1
     * data : [{"Id":"1","order_num":"1478766113","title":"微⑧条","img_src":"http://imgcdn.lerays.com/tag/2016/08/08/52305065adb7c2ea983297c8bcdeb985.jpg!320","type":"0","subscribers":1072,"is_subscribe":false,"last_pubtime":"1478685485"},{"Id":"4","order_num":"1478766114","title":"星座下午茶","img_src":"http://imgcdn.lerays.com/tag/2016/08/08/9879cd0312892dde09ae883e116fb9f0.jpg!320","type":"0","subscribers":1249,"is_subscribe":false,"last_pubtime":"1478765426"},{"Id":"7","order_num":"1478766115","title":"深夜放毒","img_src":"http://imgcdn.lerays.com/tag/2016/08/08/5263fbad5004116ab85766aabe68ea54.jpg!320","type":"0","subscribers":930,"is_subscribe":false,"last_pubtime":"1478523615"},{"Id":"11","order_num":"1478766116","title":"萌萌哒","img_src":"http://imgcdn.lerays.com/tag/2016/10/20/03dd3f9430bb94b5fafad0db0819bc25.png!320","type":"0","subscribers":1287,"is_subscribe":false,"last_pubtime":"1478714419"}]
     * ext : {"c_time":1478766414}
     */

    private boolean status;
    private int count;
    private ExtBean ext;
    private List<DataBean> data;

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

    public ExtBean getExt() {
        return ext;
    }

    public void setExt(ExtBean ext) {
        this.ext = ext;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class ExtBean {
        /**
         * c_time : 1478766414
         */

        private int c_time;

        public int getC_time() {
            return c_time;
        }

        public void setC_time(int c_time) {
            this.c_time = c_time;
        }
    }

    public static class DataBean {
        /**
         * Id : 1
         * order_num : 1478766113
         * title : 微⑧条
         * img_src : http://imgcdn.lerays.com/tag/2016/08/08/52305065adb7c2ea983297c8bcdeb985.jpg!320
         * type : 0
         * subscribers : 1072
         * is_subscribe : false
         * last_pubtime : 1478685485
         */

        private String Id;
        private String order_num;
        private String title;
        private String img_src;
        private String type;
        private int subscribers;
        private boolean is_subscribe;
        private String last_pubtime;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
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

        public boolean isIs_subscribe() {
            return is_subscribe;
        }

        public void setIs_subscribe(boolean is_subscribe) {
            this.is_subscribe = is_subscribe;
        }

        public String getLast_pubtime() {
            return last_pubtime;
        }

        public void setLast_pubtime(String last_pubtime) {
            this.last_pubtime = last_pubtime;
        }
    }
}
