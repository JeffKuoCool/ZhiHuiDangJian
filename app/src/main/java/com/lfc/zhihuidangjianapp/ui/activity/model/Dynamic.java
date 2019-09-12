package com.lfc.zhihuidangjianapp.ui.activity.model;

/**
 * @date: 2019-08-04
 * @autror: guojian
 * @description:
 */
public class Dynamic {

        private String author;
        private String comment;
        private String create_code;
        private String createName;
        private long create_time;
        private String dynamic_source;
        private int if_pass;
        private int if_top;
        private int partyDynamicId;
        private int partyDynamicType;
        private String thumbnailUrl;
        private String title;
        private String abstracts;
        private String releaseDate;

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public void setAuthor(String author) {
            this.author = author;
        }
        public String getAuthor() {
            return author;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
        public String getComment() {
            return comment;
        }

        public void setCreate_code(String create_code) {
            this.create_code = create_code;
        }
        public String getCreate_code() {
            return create_code;
        }

        public void setCreate_name(String create_name) {
            this.createName = create_name;
        }
        public String getCreate_name() {
            return createName;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }
        public long getCreate_time() {
            return create_time;
        }

        public void setDynamic_source(String dynamic_source) {
            this.dynamic_source = dynamic_source;
        }
        public String getDynamic_source() {
            return dynamic_source;
        }

        public void setIf_pass(int if_pass) {
            this.if_pass = if_pass;
        }
        public int getIf_pass() {
            return if_pass;
        }

        public void setIf_top(int if_top) {
            this.if_top = if_top;
        }
        public int getIf_top() {
            return if_top;
        }

        public void setParty_dynamic_id(int party_dynamic_id) {
            this.partyDynamicId = party_dynamic_id;
        }
        public int getParty_dynamic_id() {
            return partyDynamicId;
        }

        public void setParty_dynamic_type(int party_dynamic_type) {
            this.partyDynamicType = party_dynamic_type;
        }
        public int getParty_dynamic_type() {
            return partyDynamicType;
        }



        public void setThumbnail_url(String thumbnail_url) {
            this.thumbnailUrl = thumbnail_url;
        }
        public String getThumbnail_url() {
            return thumbnailUrl;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }
    
}
