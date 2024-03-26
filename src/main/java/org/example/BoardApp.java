package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class BoardApp {
    ArrayList<Article> articleList = new ArrayList<>();
    Scanner scan = new Scanner(System.in);
    int latestArticleId = 4;
    int WRONG_VALUE = -1;

    public void run() {

        makeTestData();
        while (true) {
            System.out.print("명령어 : ");
            String cmd = scan.nextLine();
            if (cmd.equals("exit")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            } switch (cmd){
                case "add" -> add();
                case "list" -> list();
                case "update" -> update();
                case "delete" -> delete();
                case "detail" -> detail();
                case "search" -> search();
                default -> System.out.println("잘못된 명령어입니다.");
            }
        }


    }
    public void makeTestData(){
        Article a1 = new Article(1, "안녕하세요 반갑습니다. 자바 공부중이에요.", "냉무", getCurrentDateTime(), 0);
        Article a2 = new Article(2, "자바 질문좀 할게요~", "냉무", getCurrentDateTime(), 0);
        Article a3 = new Article(3, "정처기 따야되나요?", "냉무", getCurrentDateTime(), 0);
        articleList.add(a1);
        articleList.add(a2);
        articleList.add(a3);
    }
    public void add(){
        System.out.print("게시물 제목을 입력해주세요 : ");
        String title = scan.nextLine();
        System.out.print("게시물 내용을 입력해주세요 : ");
        String body = scan.nextLine();
        Article article = new Article(latestArticleId, title, body, getCurrentDateTime(), 0);
        articleList.add(article);
        System.out.println("게시물이 등록되었습니다.");
        latestArticleId++;
    }
    public void list(){
        printArticleList(this.articleList);
    }
    public void update(){
        System.out.print("수정할 게시물 번호 : ");
        int inputId = getParamAsInt(scan.nextLine(), WRONG_VALUE);
        if (inputId == WRONG_VALUE) {
            return;
        }
        Article article = findArticleById(inputId);
        if (article == null) {
            System.out.println("없는 게시물 번호입니다.");
            return;
        }
        System.out.print("새제목 : ");
        String newTitle = scan.nextLine();
        System.out.print("새내용 : ");
        String newBody = scan.nextLine();
        article.setTitle(newTitle);
        article.setBody(newBody);
        System.out.printf("%d 번 게시물이 수정되었습니다\n", inputId);
    }
    public void delete(){
        System.out.print("삭제할 게시물 번호 : ");
        int inputId = getParamAsInt(scan.nextLine(), WRONG_VALUE);
        if (inputId == WRONG_VALUE) {
            return;
        }
        Article article = findArticleById(inputId);
        if (article == null) {
            System.out.println("없는 게시물 번호입니다.");
            return;
        }
        articleList.remove(article);
        System.out.printf("%d 번 게시물이 삭제되었습니다.\n", inputId);
    }
    public void detail(){
        System.out.print("상세보기 할 게심물 번호를 입력해주세요 : ");
        int inputId = getParamAsInt(scan.nextLine(), WRONG_VALUE);
        if (inputId == WRONG_VALUE) {
            return;
        }
        Article article = findArticleById(inputId);
        if (article == null) {
            System.out.println("존재하지 않는 게시물 번호입니다.");
            return;
        }
        article.increaseHit();
        System.out.println("번호 : " + article.getId());
        System.out.println("제목 : " + article.getTitle());
        System.out.println("내용 : " + article.getBody());
        System.out.println("날짜 : " + article.getRegDate());
        System.out.println("조회수 : " + article.getHit());
        System.out.println("===========");
    }
    public void search(){
        System.out.print("검색 키워드를 입력해주세요 : ");
        String keyword = scan.nextLine();
        ArrayList<Article> searchedList = new ArrayList<>();
        for (int i = 0; i < articleList.size(); i++) {
            Article article = articleList.get(i);
            if (article.getTitle().contains(keyword)) {
                searchedList.add(article);
            }
        }
        printArticleList(searchedList);

    }

    public Article findArticleById(int id) {
        for (int i = 0; i < articleList.size(); i++) {
            Article article = articleList.get(i);
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
    }
    public String getCurrentDateTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        String formattedDate = now.format(formatter);
        return formattedDate;
    }
    public void printArticleList(ArrayList<Article> targetList){
        if(targetList.size()==0){
            System.out.println("검색결과가 없습니다.");
            return;
        }
        System.out.println("==============");
        for (int i = 0; i < targetList.size(); i++) {
            Article article = targetList.get(i);
            System.out.println("번호 : " + article.getId());
            System.out.println("제목 : " + article.getTitle());
        }
    }
    public int getParamAsInt(String Param, int defaultValue){
        try {return Integer.parseInt(Param);

        }
        catch (NumberFormatException e){
            System.out.println("숫자로 입력해주세요 ");
            return defaultValue;
        }
    }
}
