package com.qiufeng.blog.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Image;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;

public class MarkdownUtils {

    /**
     * 将markdown转换为html
     * @param markdown
     * @return html
     */
    public static String  markdownToHtml(String markdown){
        Parser build = Parser.builder().build();
        Node parse = build.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(parse);
    }

    /**
     * 增加扩展（标题锚点，表格生成）
     * 将markdown转换为html
     * @param markdown
     * @return
     */
    public static String markdownToHtmlExtensions(String markdown){
        //h标题生成id
        Set<Extension> singleton = Collections.singleton(HeadingAnchorExtension.create());
        //转换table的html
        List<Extension> extensions = Arrays.asList(TablesExtension.create());
        Parser build = Parser.builder().extensions(extensions).build();
        Node parse = build.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(singleton)
                .extensions(extensions)
                .attributeProviderFactory(new AttributeProviderFactory() {
                    @Override
                    public AttributeProvider create(AttributeProviderContext attributeProviderContext) {
                        return new CustomAttributeProvider();
                    }
                }).build();
        return renderer.render(parse);
    }

    /**
     * 处理标签属性
     */
    static class CustomAttributeProvider implements AttributeProvider{

        @Override
        public void setAttributes(Node node, String s, Map<String, String> map) {
            //改变a标签的target属性为_blank
            if (node instanceof Link){
                map.put("target","_blank");
            }
            if (node instanceof TableBlock){
                map.put("class","ui celled table");
            }
        }
    }

    static public String myMarkdownToHtml(String md){
        Parser parser = Parser.builder().build();
        Node document = parser.parse(md);
        HtmlRenderer renderer = HtmlRenderer.builder().
                attributeProviderFactory(new AttributeProviderFactory() {
                    @Override
                    public AttributeProvider create(AttributeProviderContext attributeProviderContext) {
                        return new PAttributeProvider();
                    }
                }).
                build();
        String mdHtml = renderer.render(document);
        return mdHtml;
    }

    static class PAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            if (node instanceof Image) {
                attributes.put("style", "width:150px;height:200px;position:relative;left:50%;margin-left:-100px;");
            }
            if(node instanceof Link){
                attributes.put("target", "_blank");
            }
        }
    }


}
