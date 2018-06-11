package testRak;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
//trie node

class TrieNode{
	private char value;
	private HashMap<Character,TrieNode> children;
	private boolean isEnd;
	TrieNode(char ch){
		value=ch;
		children=new HashMap<>();
		isEnd=false;
		//children
	}
	
	public HashMap<Character,TrieNode> getChildren(){return children;}
	public char getValue(){return value;}
	public void setValue(char val) {value=val;}
	public void setIsEnd(boolean val) {isEnd=val;}
	public boolean isEnd() {return isEnd;}
	
	
}

class Trie{
	private TrieNode root;
	Trie(){
		root=new TrieNode((char)0);
		
	}
	
	public TrieNode returnRoot() {
		return root;
	}
	
	public void insert(String word) {
		int length=word.length();
		TrieNode currNode=root;		
		for(int level=0;level<length;level++) {
			HashMap<Character,TrieNode> child = currNode.getChildren();
			char ch=word.charAt(level);
			if(Character.isAlphabetic(ch)){
				if(child.containsKey(ch)) {
					
					currNode=child.get(ch);
				}
				else {
					TrieNode temp=new TrieNode(ch);
					child.put(ch, temp);
					currNode=temp;
				}
			}
		}
		if(!currNode.equals(root))
			currNode.setIsEnd(true);		
		
	}
	
	public void blackList(String word) {
		System.out.println("Black list: "+word);
			int length=word.length();
			TrieNode currNode=root;
			for(int level=0;level<length;level++) {
				HashMap<Character,TrieNode> child = currNode.getChildren();
				char ch=word.charAt(level);
					currNode=child.get(ch);					
					if(currNode.isEnd() && level==(length-1)) {
						currNode.setValue('*');
						
					}
					
				}
		
		}
	

	public boolean search(String word) {
		int length=word.length();
		TrieNode currNode=root;
		char ch = 0;
		for(int i=0;i<word.length();i++) {
			HashMap<Character,TrieNode> child = currNode.getChildren();
			//while(currNode.isEnd()) {
				ch=word.charAt(i);
				if(child.containsKey(ch)) {
					currNode=child.get(ch);
				}
				else
					return false;
			//}
		}
		return (currNode.getValue()==ch&&currNode.isEnd());
		
	}
	int foundCount=0,searchCount=0;
	double percent=0;
	public boolean compareNodes(TrieNode fileNode,TrieNode searchNode) {
		//System.out.println("***s "+searchNode.getValue()+"-"+searchNode.isEnd()+" : "+"***f "+fileNode.getValue()+"-"+fileNode.isEnd());
		//System.out.println("***f "+fileNode.isEnd());
		if((fileNode.getValue()!=(char)0)&&(fileNode.getValue()!=(char)0)&&(fileNode.getValue()==searchNode.getValue())&&(fileNode.isEnd()&&searchNode.isEnd())) {
			foundCount++;
			if(((float)foundCount/(float)(searchCount))*100.0>=percent)
			{
				System.out.println(((float)foundCount/(float)(searchCount))*100.0);
				return true;
				//break;
				//System.out.println(str);
			}
			return false;
		}
		else {
			if(!searchNode.isEnd()) {
				HashMap<Character,TrieNode> child = fileNode.getChildren();
				HashMap<Character,TrieNode> searchNodesChild = searchNode.getChildren();
				for(char key:searchNodesChild.keySet()) {
					if(child.containsKey(key))
					{
						if(compareNodes(child.get(key),searchNodesChild.get(key))) {
							return true;
						}
						else {
							continue;
						}
						
					}
					else
						continue;
				}
			}
			
			
		
		}
		return false;
	}
	
	public boolean searchTrie(Trie searchTermsTrie1,int searchCount1,double percent1) {
		TrieNode currNode=root;
		searchCount=searchCount1;
		percent=percent1;
		
		TrieNode searchCurrNode=(TrieNode)searchTermsTrie1.returnRoot();
	
		return compareNodes(currNode,searchCurrNode);
	
}
}
class TrieComplete{
	private boolean isAllowed;
	private Trie trie;
	TrieComplete(Trie trie){
		isAllowed = true;
		this.trie=trie;
	}
	public void restrictFile() {
		isAllowed=false;
	}
	public Trie returnTrie() {
		return trie;
	}
	public boolean returnAccessStatus() {
		return isAllowed;
	}
	
}
// Java implementation of search and insert operations
// on Trie
public class TestRak {
	
	// Driver
	public static void main(String args[])
	{
		long start = System.currentTimeMillis();
		
		
		List<String> resultFileNames=new ArrayList<String>();
		List<String> searchTerms=new ArrayList<String>();
		List<String> blockTerms=new ArrayList<String>();
		//String blockTerms[];
		try {
			BufferedReader br1= new BufferedReader(new FileReader("C:\\Users\\jansiranip\\Documents\\Trie\\file1.txt"));
			String line;
			while((line=br1.readLine())!=null) {
				String[] wordsList = line.split(" ");
				//System.out.println(wordsList.length);
				for(int i=0;i<wordsList.length;i++) {
					searchTerms.add(wordsList[i].toLowerCase().trim());
					blockTerms.add(wordsList[i].toLowerCase().trim());
				}
				

			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
		//String searchTerms[] = {"hi", "apple", "hello", "welcome1"};
		//String blockTerms[] = {"hi", "apple", "hello", "welcome1"};
		double percent=60.0;
		File file = new File("C:\\Users\\jansiranip\\Documents\\Trie\\FileList.txt");
		Hashtable<String,TrieComplete> TrieMap=new Hashtable();
		Hashtable<String,String> hashList=new Hashtable<String, String>();
		
		try {
			BufferedReader br= new BufferedReader(new FileReader(file));			
			String fileName;
			try {
				
				while((fileName=br.readLine())!=null) {
					//lineC++;
					System.out.println(fileName);
					File file1 = new File(fileName);
					String Word;
					Trie trie=new Trie();
					try {
						int lineC=0;
						BufferedReader br1= new BufferedReader(new FileReader(file1));
						String line;
						while((line=br1.readLine())!=null) {
							String[] wordsList = line.split(" ");
							
							for(int i=0;i<wordsList.length;i++) {
								trie.insert(wordsList[i].toLowerCase().trim());
								hashList.put(wordsList[i],wordsList[i]);
							}
							
							lineC++;
						}
						//System.out.println("line Count: "+lineC);
						
						TrieComplete trieFinal=new TrieComplete(trie);
						
						//trieDictionary.put("YES",trie);
						TrieMap.put(fileName, trieFinal);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//--------SEARCH THRU WORD
		Set<String> keys=TrieMap.keySet();
		Iterator<String> itr=keys.iterator();
		
		long startNano1 = System.nanoTime();
		while(itr.hasNext()) {
		int foundCount=0;
		String str=itr.next();
		System.out.println("---------"+str);
		TrieComplete returnedValue=(TrieComplete)TrieMap.get(str);
		//System.out.println(returnedValue.returnAccessStatus());
		//System.out.println(returnedValue.returnTrie());
		Trie returnedTrie=returnedValue.returnTrie();
		if(returnedValue.returnAccessStatus()) {
			for(int i=0;i<searchTerms.size();i++){
				if(returnedTrie.search(searchTerms.get(i))) {
					foundCount++;
					System.out.println(((float)foundCount/(float)(searchTerms.size()))*100.0);
					if(((float)foundCount/(float)(searchTerms.size()))*100.0>=percent)
					{
						System.out.println(((float)foundCount/(float)(searchTerms.size()))*100.0);
						resultFileNames.add(str);
						break;
						//System.out.println(str);
					}
					}
			}
		}
		
	}
		long endNano1 = System.nanoTime();
		System.out.print("Execution time is " + (endNano1 - startNano1) + " ns\n");
	System.out.println("First File list");
	for(int i=0;i<resultFileNames.size();i++) {
		System.out.println(resultFileNames.get(i));
	}
	
	//Search thru TRIE
		itr=keys.iterator();
		//searchTerms[] = {"hi", "apple", "hello", "welcome1"};
		//percent=60.0;
		
		Trie searchTrie=new Trie();
		for(int i=0;i<searchTerms.size();i++) {
			searchTrie.insert(searchTerms.get(i));
		}
		
		
		resultFileNames.clear();
		long startNano = System.nanoTime();
		while(itr.hasNext()) {
		
		String str=itr.next();
		System.out.println("---------"+str);
		TrieComplete returnedValue=(TrieComplete)TrieMap.get(str);
		
		
		Trie returnedTrie=returnedValue.returnTrie();
		//Iterate trie to find out the words
		
		
		if(returnedValue.returnAccessStatus()) {
			
			if(returnedTrie.searchTrie(searchTrie,searchTerms.size(),percent)) {
				resultFileNames.add(str);
			}
			
		}
		
	}
		long endNano = System.nanoTime();
		System.out.print("Execution time is " + (endNano - startNano) + " ns\n");
	System.out.println("Last File list");
	for(int i=0;i<resultFileNames.size();i++) {
		System.out.println(resultFileNames.get(i));
	}
	
	//Search Thru HASH LIST
	long startNano2 = System.nanoTime();
	int foundCount=0;
	System.out.println(hashList.keySet().size());
	//for(String key:hashList.keySet()) {
		for(int i=0;i<searchTerms.size();i++){
		if(hashList.containsKey(searchTerms.get(i)))
			foundCount++;
		//System.out.println(((float)foundCount/(float)(searchTerms.size()))*100.0);
		if(((float)foundCount/(float)(searchTerms.size()))*100.0>=percent)
		{
			//System.out.println(((float)foundCount/(float)(searchTerms.size()))*100.0);
			//resultFileNames.add(str);
			break;
			//System.out.println(str);
		}
		
			}
	

	long endNano2 = System.nanoTime();
	System.out.print("Execution time is " + (endNano2 - startNano2) + " ns\n");
	
	// File write block listed words
	
//BLACK LIST WORDS
	
	
	itr=keys.iterator();
	//percent=100.0;
		//Black list words
		while(itr.hasNext()) {
			foundCount=0;
			String str=itr.next();
			List<String> foundWords =new ArrayList<String>();
			//System.out.println(TrieMap.get(str));
			TrieComplete returnedValue=(TrieComplete)TrieMap.get(str);
			//System.out.println("file status: "+returnedValue.returnAccessStatus());
			//System.out.println(returnedValue.returnTrie());
			Trie returnedTrie=returnedValue.returnTrie();
			if(returnedValue.returnAccessStatus()) {
				for(int i=0;i<blockTerms.size();i++){
					if(returnedTrie.search(blockTerms.get(i))) {
						foundCount++;
						foundWords.add(blockTerms.get(i));
						System.out.println(((float)foundCount/(float)(blockTerms.size()))*100.0);
						if(((float)foundCount/(float)(blockTerms.size()))*100.0>=percent)
						{
							returnedValue.restrictFile();
							
							System.out.println("100 percent");
							
							break;
							
						}
						}
				}
				if(((float)foundCount/(float)(blockTerms.size()))*100.0<percent)
				{
					//System.out.println("Not 100 percent" + foundWords.size());
					String ENDL = System.getProperty("line.separator");

					for(int i=0;i<foundWords.size();i++)
					{
						returnedTrie.blackList(foundWords.get(i));
						StringBuilder sb = new StringBuilder();
						BufferedReader br;
						try {
							br = new BufferedReader(new FileReader(str));
						
				        String ln;
				      
							while((ln = br.readLine()) != null)
							{
								String[] wordsList=ln.split(" ");
								
							   // ln.replace(foundWords.get(i), "***");
							    sb.append(ln
						                .replace(foundWords.get(i), "***")
						            ).append(ENDL);  
							    
							}
						
				        br.close();

				        BufferedWriter bw = new BufferedWriter(new FileWriter(str));
				        bw.write(sb.toString());
				        bw.close();
				        } catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}
			
		}
		
		
		
	
		long end = System.currentTimeMillis();

		NumberFormat formatter = new DecimalFormat("#0.00000");
		System.out.print("Execution time is " + formatter.format((end - start) / 1000d) + " seconds\n");
		System.out.print("Execution time is " + (end - start) + " ms\n");
		// Input keys (use only 'a' through 'z' and lower case)
		
	
		
	
	
		// Search for different keys
		/*if(search("the") == true)
			System.out.println("the --- " + output[1]);
		else System.out.println("the --- " + output[0]);
		
		if(search("these") == true)
			System.out.println("these --- " + output[1]);
		else System.out.println("these --- " + output[0]);
		
		if(search("their") == true)
			System.out.println("their --- " + output[1]);
		else System.out.println("their --- " + output[0]);
		
		if(search("thaw") == true)
			System.out.println("thaw --- " + output[1]);
		else System.out.println("thaw --- " + output[0]);*/
		
	}
}
// This code is contributed by Sumit Ghosh
