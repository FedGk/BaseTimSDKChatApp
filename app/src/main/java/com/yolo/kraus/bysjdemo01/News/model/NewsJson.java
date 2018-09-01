package com.yolo.kraus.bysjdemo01.News.model;

import java.util.List;

public class NewsJson {


    /**
     * success : true
     * code :
     * msg :
     * requestId : 93436b78dea54c5a9ea120cc2553729b
     * data : {"count":1,"first_id":"127658387468983303","last_id":"127658387468983303","news":[{"news_id":"127658387468983303","title":"为纪念北京奥运十周年，北京昌平举行铁人三项赛","source":"新华社","gmt_publish":1534725060000,"hot_index":69,"selection":false,"category":["体育"],"thumbnail_img":[],"url":"https://i.xinwen.cn/127658387468983303.html","summary_create_time":null,"summary_update_time":null,"summary":null,"content":null}]}
     */

    private boolean success;
    private String code;
    private String msg;
    private String requestId;
    private DataBean data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * count : 1
         * first_id : 127658387468983303
         * last_id : 127658387468983303
         * news : [{"news_id":"127658387468983303","title":"为纪念北京奥运十周年，北京昌平举行铁人三项赛","source":"新华社","gmt_publish":1534725060000,"hot_index":69,"selection":false,"category":["体育"],"thumbnail_img":[],"url":"https://i.xinwen.cn/127658387468983303.html","summary_create_time":null,"summary_update_time":null,"summary":null,"content":null}]
         */

        private int count;
        private String first_id;
        private String last_id;
        private List<NewsBean> news;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getFirst_id() {
            return first_id;
        }

        public void setFirst_id(String first_id) {
            this.first_id = first_id;
        }

        public String getLast_id() {
            return last_id;
        }

        public void setLast_id(String last_id) {
            this.last_id = last_id;
        }

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public static class NewsBean {
            /**
             * news_id : 127658387468983303
             * title : 为纪念北京奥运十周年，北京昌平举行铁人三项赛
             * source : 新华社
             * gmt_publish : 1534725060000
             * hot_index : 69
             * selection : false
             * category : ["体育"]
             * thumbnail_img : []
             * url : https://i.xinwen.cn/127658387468983303.html
             * summary_create_time : null
             * summary_update_time : null
             * summary : null
             * content : null
             */

            private String news_id;
            private String title;
            private String source;
            private long gmt_publish;
            private int hot_index;
            private boolean selection;
            private String url;
            private Object summary_create_time;
            private Object summary_update_time;
            private Object summary;
            private Object content;
            private List<String> category;
            private List<String> thumbnail_img;

            public String getNews_id() {
                return news_id;
            }

            public void setNews_id(String news_id) {
                this.news_id = news_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public long getGmt_publish() {
                return gmt_publish;
            }

            public void setGmt_publish(long gmt_publish) {
                this.gmt_publish = gmt_publish;
            }

            public int getHot_index() {
                return hot_index;
            }

            public void setHot_index(int hot_index) {
                this.hot_index = hot_index;
            }

            public boolean isSelection() {
                return selection;
            }

            public void setSelection(boolean selection) {
                this.selection = selection;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public Object getSummary_create_time() {
                return summary_create_time;
            }

            public void setSummary_create_time(Object summary_create_time) {
                this.summary_create_time = summary_create_time;
            }

            public Object getSummary_update_time() {
                return summary_update_time;
            }

            public void setSummary_update_time(Object summary_update_time) {
                this.summary_update_time = summary_update_time;
            }

            public Object getSummary() {
                return summary;
            }

            public void setSummary(Object summary) {
                this.summary = summary;
            }

            public Object getContent() {
                return content;
            }

            public void setContent(Object content) {
                this.content = content;
            }

            public List<String> getCategory() {
                return category;
            }

            public void setCategory(List<String> category) {
                this.category = category;
            }

            public List<?> getThumbnail_img() {
                return thumbnail_img;
            }

            public void setThumbnail_img(List<String> thumbnail_img) {
                this.thumbnail_img = thumbnail_img;
            }
        }
    }
}
