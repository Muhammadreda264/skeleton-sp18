import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class pic{

    public String [] tags;
    public int num;
    public int current;
    public pic [] scorce=new pic[main.size];
    private boolean isvisted;
    public pic(int num){
        this.tags=new String[num];
        this.current=0;
    }

    public void addtag(String tag){
        tags[current]=tag;
        current++;
    }

    public int score(pic p){
        String [] tags1=this.tags;
        String [] tags2=p.tags;
        List<String> alist = Arrays.asList(tags1);
        List<String> blist = Arrays.asList(tags2);
        HashSet<String> aset =  new HashSet<>(alist);
        int common=tags1.length-tags2.length-aset.size();
        int infirst=0;
        int insecond=0;
        int currentscore;
        Iterator<String> firstlist =alist.iterator();
        Iterator<String>secondlist=blist.iterator();
        while (firstlist.hasNext()){
            String s=firstlist.next();
            if(!blist.contains(s))
                infirst++;
        }
        while (secondlist.hasNext()){
            String s=secondlist.next();
            if(!blist.contains(s))
                insecond++;
        }


        return Math.min(common,Math.min(infirst,insecond));
    }
    public void  visit(){
        this.isvisted=true;
    }

    public int compareTo( pic p,pic c) {

        return this.score(c)-p.score(c);
    }


}
