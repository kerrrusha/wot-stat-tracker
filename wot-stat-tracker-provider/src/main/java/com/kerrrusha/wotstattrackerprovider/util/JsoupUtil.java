package com.kerrrusha.wotstattrackerprovider.util;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

public final class JsoupUtil {

    private JsoupUtil() {
        throw new UnsupportedOperationException("Util class");
    }

    public static String text(Node node) {
        String text = "";
        if (node == null) {
            return text;
        }

        if (node instanceof TextNode) {
            text = StringUtils.normalizeSpace(((TextNode) node).text());
        } else if (node instanceof Element) {
            text = StringUtils.normalizeSpace(((Element) node).text());
        }
        return text;
    }

}
