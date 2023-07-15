import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    static class Prisoner {
        Prisoner(int n,int l){
            number = n;
            boxToCheck = n;
            boxesNumbersToCheck = new ArrayList<Integer>();
            for(int ii = 0; ii<l; ii++){
                boxesNumbersToCheck.add(ii);
            }
        }
        public int number;
        public int boxToCheck;
        public List<Integer> boxesNumbersToCheck;
        
        public static List<Prisoner> getPrisonerList(int l){
            var prionsersList = new ArrayList<Prisoner>();
            for(int i = 1; i<=l;i++){
                prionsersList.add(new Prisoner(i,l));

            }
            return prionsersList;
        }
    }

    static class Box{
        Box(int n, int p){
            number = n;
            prionerNumner = p;
        }
        public int number;
        public int prionerNumner;

        public static List<Box> getBoxWithNumbersList(int l){
            var avaliableNumbers = new ArrayList<Integer>();
            for(int i = 1;i<= l;i++){
                avaliableNumbers.add(i);
            }

            var boxList = new LinkedList<Box>();
            for(int i = 1; i<=l;i++){
                boxList.add(new Box(i, getRandomIntFromListAndDeleteIt(avaliableNumbers)));
            }
            return boxList;
        }

    }

    private static int getRandomIntFromListAndDeleteIt(List<Integer> avaliableNumbersList){
        var randomPosition = (int)Math.floor(Math.random()*avaliableNumbersList.size());
        Integer randomNumber = avaliableNumbersList.get(randomPosition);
        avaliableNumbersList.remove(randomPosition);
        return randomNumber;

    }

    private static boolean isRandomSimulationCorrect(List<Prisoner> prisonerList, List<Box> boxList)
    {
        var isSucceed = true;

        for (Prisoner p: prisonerList
             ) {
            var halfLimit = prisonerList.size()/2;
            while(halfLimit>0)
            {
                int boxToCheck = getRandomIntFromListAndDeleteIt(p.boxesNumbersToCheck);
                if(boxList.get(boxToCheck).prionerNumner == p.number){
                    break;
                }
                halfLimit--;
                if(halfLimit==0) isSucceed = false;
            }
            if(!isSucceed) {
                break;
            }
        }

        return isSucceed;
    }

    private static boolean isMethodSimulationCorrect(List<Prisoner> prisonerList, List<Box> boxList)
    {
        var isSucceed = true;

        for (Prisoner p: prisonerList
        ) {
            var halfLimit = prisonerList.size()/2;
            while(halfLimit>0)
            {
                if(boxList.get(p.boxToCheck-1).prionerNumner == p.number){
                    break;
                } else {
                    p.boxToCheck = boxList.get(p.boxToCheck-1).prionerNumner;
                }
                halfLimit--;
                if(halfLimit==0) isSucceed = false;
            }
            if(!isSucceed) {
                break;
            }
        }
        return isSucceed;
    }

    public static void main(String[] args) {
        int numberOfTries = 10000000;
        int tryPerPercent = 0;


        System.out.println("");
        System.out.println("------");
        System.out.println("");


        tryPerPercent = 0;
        int randomSuccess = 0;
        for(int x = 1;x<numberOfTries;x++){
            if(isRandomSimulationCorrect(Prisoner.getPrisonerList(100),Box.getBoxWithNumbersList(100))) randomSuccess++;

            if(tryPerPercent == 0) {
                System.out.print("#");
                tryPerPercent = numberOfTries/100;
            }
            tryPerPercent--;

        }
        System.out.print(" - Success rate random: ");
        System.out.print(randomSuccess*100/numberOfTries);

        System.out.println("");
        System.out.println("------");
        System.out.println("");


        tryPerPercent = 0;
        int methodSuccess = 0;
        for(int x = 1;x<numberOfTries;x++){
            if(isMethodSimulationCorrect(Prisoner.getPrisonerList(100),Box.getBoxWithNumbersList(100))) methodSuccess++;

            if(tryPerPercent == 0) {
                System.out.print("#");
                tryPerPercent = numberOfTries/100;
            }
            tryPerPercent--;

        }
        System.out.print(" - Success rate method: ");
        System.out.print(methodSuccess*100/numberOfTries);


        System.out.println("");
        System.out.println("------");
        System.out.println("");
    }
}