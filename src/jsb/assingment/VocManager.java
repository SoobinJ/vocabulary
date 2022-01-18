package jsb.assingment;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class VocManager {

	private String userName;
	private Word[] voc=new Word[100];
	private int number=0;
	private ArrayList<Word> a = new ArrayList<>();
	
	static Scanner scan=new Scanner(System.in);

	VocManager(String userName) {
		this.userName = userName;
	}
	
	void addWord(Word word) {
		if(number<voc.length)
			voc[number++]=word;
		else
			System.out.println("단어장에 더 이상의 단어를 등록할 수 없습니다");
	}
	
	void Arrayadd(Word word) {
		a.add(word);
	}
	
	void makeVoc(String fileName) {
		
		try(Scanner scan=new Scanner(new File(fileName))){
			while(scan.hasNextLine()) {
				String str =scan.nextLine();
				String[] temp=str.split("\t");
				this.addWord(new Word(temp[0].trim(),temp[1].trim()));
				this.Arrayadd(new Word(temp[0].trim(),temp[1].trim()));
			}
			System.out.println(userName+"의 단어장이 생성되었습니다");
			this.menu();
			
		}catch(FileNotFoundException e) {
			System.out.println(userName+"의 담어장이 생성되지 않았습니다.\n파일명을 확인하세요.");
		}
	}
	
	void menu() {
		int choice=0;
		while(choice !=3) {
			System.out.println("\n-----"+userName+"의 단어장 -----");
			System.out.println("1) 단어검색 2) 객관식 퀴즈 3)종료");
			System.out.print("메뉴를 선택하세요 : ");
			choice=scan.nextInt();
			scan.nextLine();
			System.out.println();
			
			switch(choice) {
			case 1:
				searchVoc();
				break;
			case 2 : 
				quiz();
				break;
			case 3:
				System.out.println(userName+"의 단어장 프로그램을 종료합니다");
				break;
			}
		}
	}

	
	public void quiz() {
		int count=1;
		int answer=0;
		long startTime;
		long endTime;

		startTime=System.currentTimeMillis();
		do{
			int ran=(int) (Math.random()*a.size());

			String eng=a.get(ran).eng;
			String kor=a.get(ran).kor;
			a.remove(ran);

			System.out.println("---- 객관식 퀴즈 "+count+"번 -----");
			System.out.println(eng+"의 뜻은 무엇일까요?");
			int c=1;
			int an=(int)(Math.random()*4+1);
			do{
				int randnum=(int) (Math.random()*a.size());
				
				if(an==c) {
					System.out.println(c+")"+kor);
				}
				else {
					
					System.out.println(c+")"+a.get(randnum).kor);
					a.remove(randnum);
				}
				c++;
			}while(c<=4);
			System.out.print("답을 입력하세요 : ");
			try {
				int n=scan.nextInt();
				if(n==an) {
					System.out.println("정답입니다");
					answer++;
				}else {
					System.out.println("틀렸습니다.  정답은 "+an+"번입니다.");
				}
			}catch(InputMismatchException e) {
				scan=new Scanner(System.in);
				System.out.println("틀렸습니다.  정답은 "+an+"번입니다.!");
			}
			count++;
		}while(count<=10);
		endTime=System.currentTimeMillis();
		System.out.println();
		System.out.println(userName+"님 10문제 중" +answer+"개 맞추셨고,"+(endTime-startTime)/1000+"초 소요되었습니다.");

	}
	

	public void searchVoc() {
		System.out.println("---- 단어 검색 -----");
		System.out.print("검색할 단어를 입력하세요 (영단어) : ");
		String sWord=scan.nextLine();
		sWord=sWord.trim();
		
		for(Word word : voc) {
			if(word!=null) {
				if(word.eng.equals(sWord)) {
					System.out.println("단어의 뜻 : "+word.kor);
					break;
				}
			}else {
				System.out.println("단어장에 등록되어 있지 않습니다");
				break;
			}
		}
		System.out.println("---------------");
	}
}
