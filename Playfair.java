//Jackson Vaughn
//CIS3362
//Homework 3


import java.util.Scanner; 
public class Playfair{
   public static void main (String args[]){

       String KeyWord;
       String plaintext;
       String ciphertext;

       char key[][] = new char[5][5];
      
       Scanner input = new Scanner(System.in);
       
       
       int TestCases = input.nextInt();
       input.nextLine();
      

       String[] ciphertexts = new String[TestCases];
           for(int i = 0; i < TestCases; i++){  

               KeyWord = input.nextLine();
               //System.out.println("Key: " + KeyWord);
               plaintext = input.nextLine(); 
               //System.out.println("Plaintext: " + plaintext);
              
               key = CreateKeyMatrix(KeyWord);
               
               ciphertext = PlayfairEncrypt(key,plaintext);
               ciphertexts[i] = ciphertext;
           }
        

       for (int i = 0; i < TestCases; i++){
        System.out.println(ciphertexts[i]);
    }

    input.close();
            
   }
   public static String PlayfairEncrypt(char[][] key, String plaintext){
       //encrype 2 letters at a time
       String ciphertext = "";
       int x1 = 0;
       int x2 = 0;
       int y1 = 0;
       int y2 = 0;

       int len = plaintext.length();
    
       //check for duplicate letters and pad with q
            StringBuilder newPT = new StringBuilder(plaintext);

       for (int i = 0; i < len-1; i++){
           if((newPT.charAt(i) == newPT.charAt(i+1)) && newPT.charAt(i) != 'x' && newPT.charAt(i+1) != 'x'){
                newPT.insert(i+1,'x');
                len++;
            }

            
            else if(newPT.charAt(i) == 'x' && newPT.charAt(i+1) == 'x'){
                newPT.insert(i+1,'q');
                len++;
            }
                
        }
        plaintext = newPT.toString();
        int j = 1;
       if (len % 2 != 0){
           plaintext += 'x';
           len++;
       }
       String ToEncrypt;
       //so, we need to encode 2 letters at a time
       for(int i = 0; i < len; i+=2){

           // take letter at pos i and i+1
           ToEncrypt = plaintext.substring(i, i+2);
          
           for(int row = 0; row < 5; row++){

               for(int col = 0; col < 5; col++){
                  if(key[row][col] == plaintext.charAt(i)){
                   x1 = col;
                   y1 = row;

                  }
               }
           }
           for(int row = 0; row < 5; row++){

               for(int col = 0; col < 5; col++){
                  if(key[row][col] == plaintext.charAt(i+1)){
                   x2 = col;
                   y2 = row;
                  }
               }
           }

           //Same row
           if(y1 == y2){

               //for the same row, we just shift them over by one for x. the y values stay the same
                x1 = (x1 +1) % 5;
               x2 = (x2 +1) % 5;
               ciphertext +=key[y1][x1];
               ciphertext +=key[y2][x2];
           }

           //Same col find the y value down one. shift over if needed
           else if(x1 == x2){

            y1 = (y1 + 1) % 5;
            y2 = (y2 + 1) % 5;

            ciphertext +=key[y1][x1];
            ciphertext +=key[y2][x2];
           }

           //base case encrypt at the oposite letter
           else{
                    ciphertext +=key[y1][x2];        
                    ciphertext +=key[y2][x1];
            }
       }

       return ciphertext;
   }

   public static char[][] CreateKeyMatrix(String key){

       String alphabet = "abcdefghijklmnopqrstuvwxyz";

       char KeyMatrix[][] = new char[5][5];

       //concat the alphabet, this will be useful for removing dupes
       key = key.concat(alphabet);

       //frequency of chars in input
       int freq[] = new int[256];
       //String
       StringBuilder NewKey = new StringBuilder();
      //Method to remove all of the dupe chars fron the orginal key.
       for (char i: key.toCharArray()){
           //we dont use the letter i
           if(freq[i] == 0 && i != 'i'){
               NewKey.append(i);   //add to sb
               freq[i]++;  //increase freq
           }

       }
       key = NewKey.toString();

       //here we add to the matrix
       int k = 0;
       for(int i = 0; i < 5; i++){

           for(int j = 0; j < 5; j++){
               KeyMatrix[i][j] = key.charAt(k);
               k++;
           }
       }

       return KeyMatrix;
   }

}