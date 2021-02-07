package com.qiufeng.blog.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "t_blog")
public class Blog{
    /**
     * 博客id
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 博客标题
     */
    private String title;

    /**
     * 博客内容
     */
    private String content;

    /**
     * 首图
     */
    private String firstPicture;

    /**
     * 标记
     */
    private String flag;

    /**
     * 浏览次数
     */
    private Integer views;

    /**
     * 赞赏是否开启
     */
    private boolean appreciation;

    /**
     * 装载声明是否开启
     */
    private boolean shareStatement;

    /**
     * 评论是否开启
     */
    private boolean commentabled;

    /**
     * 是否发布
     */
    private boolean published;

    /**
     * 是否推荐
     */
    private boolean recommend;

    /**
     * 博客介绍
     */
    private String description;

    /**
     * 博主ID
     */
    private Long userId;

    /**
     * 博主信息
     */
    @ManyToOne
    private SysUser sysUser;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * 更新时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private  Date updateTime;

    /**
     * 类型
     */
    @ManyToOne
    private Type type;

    /**
     * 该博客的标签
     */
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    /**
    *多个id
     */
    @Transient
    private String tagIds;


    public Blog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public void init(){
        this.tagIds = tagsToIds(this.getTags());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    //将集合中的id以1,2,3的方式初始化
    private String tagsToIds(List<Tag> tags){
        if (!tags.isEmpty()){
            StringBuffer ids = new StringBuffer();
            Boolean flag = false;
            for(Tag tag:tags){
                if (flag) {
                    ids.append(",");
                }else{
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }else {
            return tagIds;
        }
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", tags=" + tags +
                ", user=" + sysUser +
                ", comments=" + comments +
                ", tagIds='" + tagIds + '\'' +
                '}';
    }
}
