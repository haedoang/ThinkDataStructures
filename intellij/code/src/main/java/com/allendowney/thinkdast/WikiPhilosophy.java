package com.allendowney.thinkdast;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WikiPhilosophy {

    final static List<String> visited = new ArrayList<String>();
    final static WikiFetcher wf = new WikiFetcher();

    /**
     * Tests a conjecture about Wikipedia and Philosophy.
     *
     * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
     *
     * 1. Clicking on the first non-parenthesized, non-italicized link
     * 2. Ignoring external links, links to the current page, or red links
     * 3. Stopping when reaching "Philosophy", a page with no links or a page
     *    that does not exist, or when a loop occurs
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String destination = "https://en.wikipedia.org/wiki/Philosophy";
        String source = "https://en.wikipedia.org/wiki/Java_(programming_language)";

        testConjecture(destination, source, 10);
    }

    /**
     * Starts from given URL and follows first link until it finds the destination or exceeds the limit.
     *
     * @param destination
     * @param source
     * @throws IOException
     */
    public static void testConjecture(String destination, String source, int limit) throws IOException {

        // 1. 페이지 내 링크 리스트를 가져오자
        Elements paragraphs = wf.fetchWikipedia(source);

        // 2. process DFS
        process(paragraphs);
    }

    private static void process(Elements elements) {

        for (Element element : elements) {
            dfs(element);

        }
    }

    private static void dfs(Element element) {
        WikiNodeIterable wikiNodeIterable = new WikiNodeIterable(element);

        for (Node node : wikiNodeIterable) {
            if (node instanceof TextNode) {
                System.out.println(node);
            }
            else if (node instanceof Element) {
                dfs((Element) node);
            }
        }
    }

}
