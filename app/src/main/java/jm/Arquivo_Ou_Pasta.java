/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jm;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 *
 * @author AnaMariana
 */
public class Arquivo_Ou_Pasta {     
    
    public static boolean deletar( File file_ou_pasta ) {  
       if ( file_ou_pasta.isDirectory() ) {  
           String[] listaDosArquivos = file_ou_pasta.list();  
           for (int i=0; i<listaDosArquivos.length; i++) {   
              boolean success = deletar( new File( file_ou_pasta, listaDosArquivos[ i ] ) );  
               if ( !success ) {  
                   return false;  
               }  
           }  
       }      
       // Agora o diretório está vazio, restando apenas deletá-lo.  
       return file_ou_pasta.delete();  
    }  
    
    public static boolean deletar_Todos_Arquivos_da_Pasta( File file_ou_pasta ) {  boolean retorno = false;
       if ( file_ou_pasta.isDirectory() ) {  
           String[] listaDosArquivos = file_ou_pasta.list();  
           for (int i=0; i<listaDosArquivos.length; i++) {   
              boolean success = deletar( new File( file_ou_pasta, listaDosArquivos[ i ] ) );  
               if ( !success ) {  
                   return false;  
               }  
           }  
           retorno = true;
       }      
       // Agora o diretório está vazio, restando apenas deletá-lo.  
       return retorno;//file_ou_pasta.delete();  
    }
    
     public static boolean criarPasta( String onde, String nomePasta ) { boolean retorno = false;
        File criar = new File( onde + System.getProperty("file.separator") + nomePasta );
        if ( !criar.exists() ) { 
               criar.mkdir(); retorno = true; 
        }
        return retorno;  
    } 
     
     public static boolean copiarArquivo( File arquivo, String destino ) { boolean retorno = false;
         try {
             InputStream inputStream = new FileInputStream( arquivo.getPath() );
             // write the inputStream to a FileOutputStream
             OutputStream outputStream = new FileOutputStream( new 
                File( destino + System.getProperty("file.separator") + arquivo.getName() ));
             // Criando
             int read = 0;
             byte[] bytes = new byte[1024];
             while ( ( read = inputStream.read( bytes ) ) != -1 ) {
                 outputStream.write( bytes, 0, read );
             }
             
             try{ inputStream.close(); outputStream.flush(); outputStream.close(); }catch(Exception e){}
             
             retorno = true; 
         } catch ( Exception e){}
         return retorno;
     }
     
     public static boolean copiarArquivoNovoNome( File arquivo, String destino, String nome ) { boolean retorno = false;
         try {
             InputStream inputStream = new FileInputStream( arquivo.getPath() );
             // write the inputStream to a FileOutputStream
             OutputStream outputStream = new FileOutputStream( new 
                File( destino + "\\" + nome ));
             // Criando
             int read = 0;
             byte[] bytes = new byte[1024];
             while ( ( read = inputStream.read( bytes ) ) != -1 ) {
                 outputStream.write( bytes, 0, read );
             }
             
             try{ inputStream.close(); outputStream.flush(); outputStream.close(); }catch(Exception e){}
             
             retorno = true; 
         } catch ( Exception e){}
         return retorno;
     }
     
     public static boolean copiarArquivoInputStream( 
             InputStream inputStream, String complemento, File arquivo, String destino ) { boolean retorno = false;
         try {
             // write the inputStream to a FileOutputStream
             OutputStream outputStream = new FileOutputStream( new 
                File( destino + System.getProperty("file.separator") + complemento+arquivo.getName() ));
             // Criando
             int read = 0;
             byte[] bytes = new byte[1024];
             while ( ( read = inputStream.read( bytes ) ) != -1 ) {
                 outputStream.write( bytes, 0, read );
             }
             
             try{ inputStream.close(); outputStream.flush(); outputStream.close(); }catch(Exception e){}
             
             retorno = true; 
         } catch ( Exception e){}
         return retorno;
     }

     
     // Se o Arquivo não existir este método criar e digita a String enviada
     // Se existir apaga o que tem e escreve a string enviada
     public static void criar_Arquivo_Iso( String end_Arquivo, String letraDig ){      
         try {
             File arq = new File(end_Arquivo);  
             OutputStream OS = (OutputStream) new FileOutputStream(arq); 
             OutputStreamWriter OSW = new OutputStreamWriter(OS, "ISO-8859-1"); // "UTF-8", "UTF-16", "ISO-8859-1" ou "US-ASCII"
             PrintWriter Print = new PrintWriter(OSW);
             Print.println(letraDig);
             Print.close();  
             OSW.close();  
             OS.close(); 
                 
	 } catch ( IOException e ){}
     } 
     
     // Se o Arquivo não existir este método criar e digita a String enviada
     // Se existir apaga o que tem e escreve a string enviada
     public static boolean criar_Arquivo_Iso_Boo( String end_Arquivo, String letraDig ){ boolean retorno = false;      
         try {
             File arq = new File(end_Arquivo);  
             OutputStream OS = (OutputStream) new FileOutputStream(arq); 
             OutputStreamWriter OSW = new OutputStreamWriter(OS, "UTF-8"); // "UTF-8", "UTF-16", "ISO-8859-1" ou "US-ASCII"
             PrintWriter Print = new PrintWriter(OSW);
             Print.println(letraDig);
             Print.close();  
             OSW.close();  
             OS.close(); 
                 
             retorno = true;
	 } catch ( IOException e ){ retorno = false; }
         
         return retorno;
     }
     
     // Se o Arquivo não existir este método criar e digita a String enviada
     // Se existir apaga o que tem e escreve a string enviada
     public static boolean criar_Arquivo_Iso_Boo_Tipo_UTFISO( String end_Arquivo, String letraDig, String tipo_UTFISO ){ boolean retorno = false;      
         try {
             File arq = new File(end_Arquivo);  
             OutputStream OS = (OutputStream) new FileOutputStream(arq); 
             OutputStreamWriter OSW = new OutputStreamWriter(OS, tipo_UTFISO); // "UTF-8", "UTF-16", "ISO-8859-1" ou "US-ASCII"
             PrintWriter Print = new PrintWriter(OSW);
             Print.println(letraDig);
             Print.close();  
             OSW.close();  
             OS.close(); 
                 
             retorno = true;
	 } catch ( IOException e ){ retorno = false; }
         
         return retorno;
     }
     
     public static File criar_Arquivo_Iso_Boo_Tipo_UTFISO_Ret_F( String end_Arquivo, String letraDig, String tipo_UTFISO ){ File retorno = null;      
         try {
             retorno = new File(end_Arquivo);  
             OutputStream OS = (OutputStream) new FileOutputStream(retorno); 
             OutputStreamWriter OSW = new OutputStreamWriter(OS, tipo_UTFISO); // "UTF-8", "UTF-16", "ISO-8859-1" ou "US-ASCII"
             PrintWriter Print = new PrintWriter(OSW);
             Print.println(letraDig);
             Print.close();  
             OSW.close();  
             OS.close(); 
	 } catch ( IOException e ){}
         
         return retorno;
     }
     
     public static void copiar(File diretorioOrigem, String destinoR) {      
        try{      
            if ( diretorioOrigem.isDirectory()  ) { String destino = destinoR + System.getProperty("file.separator") + diretorioOrigem.getName();
            
                Arquivo_Ou_Pasta.criarPasta( destinoR, diretorioOrigem.getName());
                                    
                File[] listaDosArquivos = diretorioOrigem.listFiles(); 
                    
                if ( listaDosArquivos != null ) { 
                        
                    for (int j=0; j < listaDosArquivos.length; j++) {
                                   
                        File f = new File( listaDosArquivos[j].getName() );

                        File veriF = new File( diretorioOrigem.getPath() + System.getProperty("file.separator") + f.getPath() );
                        if ( veriF.isDirectory() ) {
                                
                            copiar( veriF, destino );                            
                        } else{ Arquivo_Ou_Pasta.copiarArquivo( veriF, destino ); }
                    }
                } else { System.out.println( "listaDosArquivos == null: " +diretorioOrigem.getPath()+ "listarPastas( List<String> diretorios )" ); }
                    
            } else if ( diretorioOrigem.isFile() ) { Arquivo_Ou_Pasta.copiarArquivo( diretorioOrigem, destinoR ); }                
        } catch( Exception e ){ System.out.println( "Zero - Diretórios - Home - listarPastas( List<String> diretorios )" ); }
    }

    /*
    public static boolean copiarArquivoDoJar( String origem, String destino ) { boolean retorno = false;

        Class<XXX> clazzHome = XXX.class;
        try { File f = new File( origem );

            String s = System.getProperty("file.separator");

            InputStream inputStream = clazzHome.getResourceAsStream(origem);

            OutputStream outputStream = new FileOutputStream(new File( destino + s + f.getName() ));

            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {

                outputStream.write(bytes, 0, read);
            }

            try{ inputStream.close(); outputStream.flush(); outputStream.close(); }catch(Exception e){}

            retorno = true;
        } catch ( Exception e){
            System.out.println("////copiarArquivoDoJar////////////////////////////////////////////////" );
            System.out.println("///clazzHome////////////" + clazzHome.getResource(origem) );
            e.printStackTrace();
            System.out.println("////copiarArquivoDoJar/////////////////////////////////////////" );
        }
        return retorno;
    }
    */
              
    public static void main( String args[] ){
        //Deletar Arquivo
        /*
        String end_Arquivo = System.getProperty("user.home") + System.getProperty("file.separator") + "x" + ".jpg";
        Deletar_Arquivo_Ou_Pasta
        deletar( new File( end_Arquivo ) );
        */
        
        //Criar Pasta
        //criarPasta( System.getProperty("user.home"), "x" );
        
        //Copiar Arquivo
        /*
        String arquivo = System.getProperty("user.home") + System.getProperty("file.separator") 
                + "x" + System.getProperty("file.separator")  + "z" + ".jpg";
        copiarArquivo( new File( arquivo ), System.getProperty("user.home") );
        */
        
        //Criar Arquivo
        String arquivo = System.getProperty("user.home") + System.getProperty("file.separator") + "z" + ".txt";
        criar_Arquivo_Iso( arquivo, "<html>çççççççç</html>" );
    }
    
}
