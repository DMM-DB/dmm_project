/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dmm_db;
import java.io.*;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author PGPIT
 */
public class Main {

    /**
     * @param args the command line arguments
     */
     static TreeSet temp,tload;
    static LinkedList temp_arr,del_ll,upd2_ll,upd3_ll;
    static FileWriter f ;
    static   BufferedWriter fbw;
    static int dflag=0,iflag=0,uflag=0,d_reg_flag=0,u_reg_flag=0,upd_cont=0;
    static int change=0;

    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        BufferedReader br;
        Scanner in;

        int n;
        br=new BufferedReader(new InputStreamReader(System.in));
        temp=new TreeSet();
        tload=new TreeSet();
        del_ll=new LinkedList();
        upd2_ll=new LinkedList();
upd3_ll=new LinkedList();

        System.out.println("\n[**]");
        help();
        menu();

    }//main

    private static void help() {
        System.out.println("*****MENU******");
        System.out.println("Create:Insert str_name");
        System.out.println("Read:select");
        System.out.println("Update:update new_str old_str OR regex");
        System.out.println("Delete:delete str_name OR regex");
        System.out.println("help");
        System.out.println("exit");
    }

    private static void create(String str22) throws FileNotFoundException, IOException {

     Scanner in=new Scanner(System.in);
     int ch=-1;
     load();
     if(temp.contains(str22) || (tload!= null && tload.contains(str22) || (upd2_ll.contains(str22)) ))
     {
       System.out.println("String shud be unique");
         menu();

     }
     else
     {
        iflag=1;
        temp.add(str22);
        System.out.println("added"+str22);
       }
        System.out.println(temp);

}

    private static void read() throws FileNotFoundException, IOException {

        BufferedReader br = null;
        String sCurrentLine;

	br = new BufferedReader(new FileReader("db3.txt"));
	while ((sCurrentLine = br.readLine()) != null) {
		System.out.println(sCurrentLine);
        }
        br.close();
    }



    private static void menu() throws FileNotFoundException, IOException {
        BufferedReader br;
        Scanner in;
        String str=" ";
        String str1=" ";
        String str2=" ";
        String str3=" ";
        String str4=" ";
        int n;
        br=new BufferedReader(new InputStreamReader(System.in));

        while(true){
          in=new Scanner(System.in);
          System.out.println("\n[**]");
          str=br.readLine();
          StringTokenizer tok = new StringTokenizer(str);
          int no =tok.countTokens();
          str1=tok.nextToken();
          System.out.println("token1\t"+str1+"no"+no);
          switch(no)
           {
                case 1: if (str1.equals("select"))
                        {
                             read();
                        }
                    else if (str1.equals("commit"))
                        {
                            commit();
                    }

                else if(str1.equals("help"))
                    {
                        help();
                    }
                else if(str1.equals("exit"))
                    {
                        System.exit(0);
                    }
                    else{
                    System.out.println("invalid command");
                    }

                    break;
                 case 2:
                     if(tok.hasMoreElements())
                        str2 = tok.nextToken();
                     System.out.println("token2"+str2);

                     if(str1.equals("insert"))
                        {
                             create(str2);
                        }
                     else if (str1.equals("delete"))
                        {
                           if(str2.charAt(0)=='[')
                           {
                               del_regex(str2);
                           }
                           else
                           {
                               delete(str2);
                           }
                       }
                        else{
                            System.out.println("invalid command");
                       }
                        break;
               case 3:
                     while(tok.hasMoreTokens())
                     {
                         str2 = tok.nextToken();
                         str3 = tok.nextToken();
                     }
                   if (str1.equals("update"))
                       {
                       if(str3.charAt(0)=='[')
                       {
                       upd_regex(str2,str3);
                       }
                        else{
                           update(str2,str3);
                       }
                   }
                   else{
                   System.out.println("invalid command");
                    }
                     break;

            }//switch

        }//while

    }//menu

    private static void load() throws FileNotFoundException, IOException {
        BufferedReader in = new BufferedReader(new FileReader("db3.txt"));
        String str;
        while ((str = in.readLine()) != null)
        {
            System.out.println("file str"+str);
            tload.add(str);
        }
        System.out.println("tload"+tload);
        in.close();

   }//load

 private static void commit() throws IOException {
         Scanner in=new Scanner(System.in);
         System.out.println("iflag"+iflag);
         System.out.println("dflag"+dflag);
         System.out.println("uflag"+uflag);


         if(iflag==1)
         {
             System.out.println("commit insert");

             System.out.println(temp);
             temp_arr=new LinkedList();
             temp_arr.addAll(temp);
             System.out.println("linked list"+temp_arr);
             ListIterator liter=temp_arr.listIterator();
             while(liter.hasNext())
                  {
                     String elt = (String) liter.next();
                     System.out.println(elt);
                     System.out.println("before app");
                     f = new FileWriter("db3.txt", true);
                     fbw = new BufferedWriter(f);
                     fbw.append(elt);
                     System.out.println("after app");
                     fbw.newLine();
                     fbw.flush();
                     fbw.close();
                     System.out.println("\n contents writen  ");
                  }//while
                   temp.clear();
                   temp_arr.clear();
                   //tload.clear();
                   iflag=0;
                   //menu();

         }//if
         if(dflag==1)
         {
            System.out.println("commit delete");

             ListIterator diter=del_ll.listIterator();

            while(diter.hasNext())
            {
                String dstr=(String) diter.next();
                if(tload.contains(dstr))
                {
                  System.out.println("before delfile  ");
                   delfile(dstr);
                  System.out.println("aft delfile  ");
                  tload.remove(dstr);
                  System.out.println("tload"+tload);
                }

            }

                   del_ll.clear();
                   //tload.clear();
                   dflag=0;
                   //remove_blank();
                   //menu();
         }//if

         if(uflag==1)
         {
            ListIterator u2iter=upd2_ll.listIterator();
            ListIterator u3iter=upd3_ll.listIterator();

            while(u2iter.hasNext() && u3iter.hasNext() )
            {
                String u2str=(String) u2iter.next();
                String u3str=(String) u3iter.next();

                if(tload.contains(u3str))
                {
                    System.out.println("before update tload"+tload);
                    updfile(u2str,u3str);
                    ArrayList u=new ArrayList();
                    u.addAll(tload);
                    Collections.replaceAll(u,u3str,u2str);
                    tload.clear();              //check
                    tload.addAll(u);
                    System.out.println("after update tload"+tload);
                }

            }
                   upd2_ll.clear();
                   upd3_ll.clear();
                   //tload.clear();
                   uflag=0;
                   //menu();
         }
         tload.clear();
         menu();

 }//commit

    private static void delete(String str22) throws FileNotFoundException, IOException {
    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
    String str="";
    int k=1;
    load();
        if( temp != null && temp.contains(str22) )
        {
         temp.remove(str22);
        }
 else if(upd2_ll.contains(str22))
        {System.out.println("update not yet committed so cannot delete");}
    else
        {
        System.out.println("NOT IN TEMP");
        System.out.println("tload"+tload);
        System.out.println("str22--"+str22);


        if (tload.contains(str22))
        {
            dflag=1;
            System.out.println("del_ll_param"+str22);
            boolean add = del_ll.add(str22);
            System.out.println("del_ll"+add);
            if(d_reg_flag==1)
                return;
            else
                menu();
        }
        else{
             System.out.println("NOT FOUND");
             return;
            }
        }
    }

    private static void delfile(String dstr) throws IOException {
        File file = new File("db3.txt");
        File ftemp = File.createTempFile("file_temp", ".txt", file.getParentFile());
        String charset = "UTF-8";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(ftemp), charset));

        System.out.println("IN del_file:"+dstr);
        for (String line; (line = reader.readLine()) != null;) {
             if(!line.equals(dstr))
             {
                 System.out.println(line);
                 writer.println(line);

             }
        }

        reader.close();
        writer.close();
         file.delete();
         ftemp.renameTo(file);


    }


    private static void update(String str22, String str33) throws FileNotFoundException, IOException {
    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
    String str="";
    int k=1;
    ArrayList rep=new ArrayList();
    ArrayList rep2=new ArrayList();
    ArrayList rep22=new ArrayList();
     ArrayList rep33=new ArrayList();
    rep.addAll(temp);
    rep2.addAll(temp);
    load();
           if(temp != null && temp.contains(str33))
            {
                if(! tload.contains(str22))
                {
                System.out.println("&IN TEMP");
                Collections.replaceAll(rep,str33,str22);
                System.out.println("*IN TEMP");
                temp.clear();
                temp.addAll(rep);
                }
 else{System.out.println("str shud be unique");}
           }
           else if(tload.contains(str33) || upd2_ll.contains(str33) )
            {
                System.out.println("NOT IN TEMP");
                System.out.println("tload"+tload);
                if (tload.contains(str33))
                {
                  if( !(del_ll.contains(str33)) && !(upd2_ll.contains(str22) ) && !(tload.contains(str22)) )
                  {
                    System.out.println("tload contains" + str33);
                    uflag=1;
                    boolean add2 = upd2_ll.add(str22);
                    boolean add3 = upd3_ll.add(str33);
                    System.out.println("upd_ll"+add2+add3);
                  }

                  else{
                      System.out.println("strings shud be unique");
                      upd_cont=1;

                }
                    if(u_reg_flag==1)
                        return;
                    else
                        menu();

               }
                    if( upd2_ll.contains(str33))
                    {

                    uflag=1;
                    rep22.addAll(upd2_ll);
                    //rep33.add(upd3_ll);
                    Collections.replaceAll(rep22, str33, str22);
                    //boolean add2 = upd2_ll.add(str22);
                    //boolean add3 = upd3_ll.add(str33);
                    upd2_ll.clear();
                    upd2_ll.addAll(rep22);

                    System.out.println("rep22 upd2 "+rep22+upd2_ll);

                    }
            }

    }

    private static void updfile(String u2str, String u3str) throws IOException {

        File file = new File("db3.txt");
        File ftemp = File.createTempFile("file", ".txt", file.getParentFile());
        String charset = "UTF-8";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(ftemp), charset));
        for (String line; (line = reader.readLine()) != null;) {
            if(line.equals(u3str))
                line = line.replace(u3str,u2str);
                 writer.println(line);

        }
        reader.close();
        writer.close();
        file.delete();
        ftemp.renameTo(file);

    }

    private static void del_regex(String str22) throws FileNotFoundException, IOException {
            System.out.println("in del_regx");
            Pattern patt = Pattern.compile(str22);
            BufferedReader r = new BufferedReader(new FileReader("db3.txt"));
            String line;
            d_reg_flag=1;
            while ((line = r.readLine()) != null) {
             Matcher m = patt.matcher(line);
            int cnt=m.groupCount();
             while (m.find()) {
                 System.out.println(m.group(0));
                String group = m.group(0);
                 if(del_ll.contains(group))
                 {

                     continue;
                 }
                int start = m.start(0);
                int end = m.end(0);
                //Use CharacterIterator.substring(offset, end);


                System.out.println("##"+group);
                System.out.println("before delete**************");
                r.close();
                System.out.println("close called");

                delete(group);
                System.out.println("after delete**************");

                r = new BufferedReader(new FileReader("db3.txt"));

                dflag=1;
         }

      }
          d_reg_flag=0;
          r.close();
    }

    private static void upd_regex(String str2, String str3) throws FileNotFoundException, IOException {
        System.out.println("in upd_regx");
            Pattern patt = Pattern.compile(str3);
            BufferedReader r = new BufferedReader(new FileReader("db3.txt"));
            String line;
            u_reg_flag=1;
            while ((line = r.readLine()) != null) {
             Matcher m = patt.matcher(line);
                    System.out.println("in %%%%%");

            int cnt=m.groupCount();
             while (m.find()) {
                System.out.println(m.group(0));
                String group = m.group(0);
                if(upd_cont==1)
                {
                    System.out.println("in break");
                    break;

                }
                 if (upd3_ll.contains(group))
                 {
 System.out.println("in continue");
                        continue;
                 }
                int start = m.start(0);
                int end = m.end(0);
                //Use CharacterIterator.substring(offset, end);


                System.out.println("##"+group);
                System.out.println("before update**************");
                r.close();
                System.out.println("close called");

                update(str2,group);
                System.out.println("after update**************");

                r = new BufferedReader(new FileReader("db3.txt"));

                uflag=1;
         }


      }
          u_reg_flag=0;
           upd_cont=0;
          r.close();



    }



}//class

