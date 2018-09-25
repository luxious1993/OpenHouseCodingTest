package com.openhouseai.codingtest;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchedWordController {

    @GetMapping(value = "/application/{word}")
    public String wordsShow(@PathVariable("word") String word) throws Exception {
        Paragraph paragraph = new Paragraph();
        paragraph.find(word);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("query_text", word);
        jsonObject.put("number_of_occurrences", paragraph.list.size() );
        jsonObject.put("occurrences", paragraph.list);

        return jsonObject.toString(4);
    }

}
