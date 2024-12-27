package top.szzz666.AIChat.fast_text;

import com.github.jfasttext.JFastText;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.nukkit.item.enchantment.Enchantment.words;
import static top.szzz666.AIChat.config.MyConfig.FastTextModelPath;
import static top.szzz666.AIChat.tools.pluginUtil.nkConsole;

public class FastText {
    public JFastText jft;
    private JiebaSegmenter segmenter;

    public FastText() {
        // 初始化Jieba分词器
        segmenter = new JiebaSegmenter();
        jft = new JFastText();
        jft.loadModel(FastTextModelPath);
        nkConsole("FastText模型加载成功！");
    }


    // 计算两个句子的语义匹配分数
    public double semanticMatchingScore(String sentence1, String sentence2) {
        List<SegToken> tokens1 = segmenter.process(sentence1, JiebaSegmenter.SegMode.SEARCH);
        List<String> words = tokens1.stream().map(token -> token.word).collect(Collectors.toList());
        List<SegToken> tokens2 = segmenter.process(sentence2, JiebaSegmenter.SegMode.SEARCH);
        List<String> words2 = tokens2.stream().map(token -> token.word).collect(Collectors.toList());
        // 获取句子的向量表示
        List<Float> vector1 = getSentenceVector(words);
        List<Float> vector2 = getSentenceVector(words2);
        return cosineSimilarity(vector1, vector2);
    }

    // 获取句子的向量表示
    private List<Float> getSentenceVector(List<String> words) {
        List<Float> sentenceVector = new ArrayList<>();
        for (String word : words) {
            List<Float> wordVector = jft.getVector(word);
            if (wordVector != null) {
                if (sentenceVector.isEmpty()) {
                    sentenceVector.addAll(wordVector);
                } else {
                    for (int i = 0; i < wordVector.size(); i++) {
                        sentenceVector.set(i, sentenceVector.get(i) + wordVector.get(i));
                    }
                }
            }
        }
        return sentenceVector;
    }

    // 计算余弦相似度
    private double cosineSimilarity(List<Float> vec1, List<Float> vec2) {
        if (vec1.size() != vec2.size()) {
            throw new IllegalArgumentException("Vectors must be of the same length");
        }
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vec1.size(); i++) {
            dotProduct += vec1.get(i) * vec2.get(i);
            normA += Math.pow(vec1.get(i), 2);
            normB += Math.pow(vec2.get(i), 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

}
