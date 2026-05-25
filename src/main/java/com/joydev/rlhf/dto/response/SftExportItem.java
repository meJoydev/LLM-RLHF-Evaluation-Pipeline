package com.joydev.rlhf.dto.response;

public class SftExportItem {
    private String prompt;
    private String response;
    private String domain;

    public SftExportItem() {}
    public SftExportItem(String prompt, String response, String domain) {
        this.prompt = prompt; this.response = response; this.domain = domain;
    }

    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }
    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }
    public String getDomain() { return domain; }
    public void setDomain(String domain) { this.domain = domain; }
}
