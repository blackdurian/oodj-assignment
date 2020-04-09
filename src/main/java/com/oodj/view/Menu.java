package com.oodj.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static List<MenuItem> items = new ArrayList<>();

    private String header;
    private String topMessage;
  //  private static List<Class<?>> menus = new ArrayList<>();;
  private static final String BREAK_LINE = "====================================================";
    private Menu(){
     //menus = findAnnotatedMenu();
    }

    private static class MenuHolder {
        private static final Menu INSTANCE = new Menu();
    }

    public static Menu getInstance() {
        return MenuHolder.INSTANCE;
    }

    public void display(){
        printHeader();
        printTopMessage();
        printItems();
        String request = scanRequest().trim();
        //Todo request validation
        //     breakLine();
        MenuEvent menuEvent = findMenuEventByKey(request);
        if (menuEvent!=null){
            menuEvent.execute();
        }else {
            System.out.println(request + " - key is not found");
            display();
        }

    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void printHeader() {
        int spaceNum = BREAK_LINE.length() / 2 - header.length() / 2;
        String midText = String.join("", Collections.nCopies(spaceNum, " ")) + header;
        System.out.println(BREAK_LINE);
        System.out.println(midText);
        System.out.println(BREAK_LINE);
    }

    public void setTopMessage(String topMessage) {
        this.topMessage = topMessage;
    }

    public void printTopMessage() {
        System.out.print(topMessage);
    }
/*
    public void respond(String value){

        for (Class<?> cl : menus) {
            MenuRequest controller = cl.getAnnotation(MenuRequest.class);
            if (controller.value().equals(value)){
                try {
                    clear();
                    cl.newInstance();
                    execute();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<Class<?>> findAnnotatedMenu(){
        Reflections ref = new Reflections("com.oodj.controller");
        return new ArrayList<>(ref.getTypesAnnotatedWith(MenuRequest.class));
    }

    private List<MenuItem> findAnnotatedItems(Class<?> clazz, List<MenuItem> items){
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if(annotation instanceof MenuRequest){
                    try {
                        method.invoke(items, (Object[])null);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return items;
    }
*/

    public void addItem(MenuItem menuItem){
        items.add(menuItem);
    }

    public void clear(){
        items.clear();
        topMessage = "";
        header = "";
    }


    private void printItems(){
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("#%d\t%s%n",i+1,items.get(i).getName());
        }
    }


    private MenuEvent findMenuEventByKey(String key){
        for (int i = 0; i < items.size(); i++) {
            MenuItem item = items.get(i);
            if (Arrays.asList(item.getKeys()).contains(key.toLowerCase())||key.equals(String.valueOf(i+1))) {
                return item.getMenuEvent();
            }
        }
        return null;
    }

    private String scanRequest(){
        Scanner scanner = new Scanner(System.in);
        return scanner. nextLine();
    }


}
