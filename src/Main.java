import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    static class Prisoner {
        Prisoner(int n,ArrayList<Integer> boxListToCheck){
            number = n;
            boxToCheck = n;
            boxesNumbersToCheck = boxListToCheck;
        }
        public int number;
        public int boxToCheck;
        public List<Integer> boxesNumbersToCheck;
        
        public static List<Prisoner> getPrisonerList(int l){

            var boxesNumbersToCheckListToCopy = new ArrayList<Integer>();
            for(int ii = 0; ii<l; ii++){
                boxesNumbersToCheckListToCopy.add(ii);
            }

            var prionsersList = new ArrayList<Prisoner>();
            for(int i = 1; i<=l;i++){
                prionsersList.add(new Prisoner(i,(ArrayList)boxesNumbersToCheckListToCopy.clone()));

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
        int numberOfTries = 100;
        int numberOfPrisoners = 100;
        int tryPerPercent = numberOfTries/100;
        int randomSuccess = 0;
        int methodSuccess = 0;

        for(int x = 1;x<numberOfTries;x++){
            var prisonersList = Prisoner.getPrisonerList(numberOfPrisoners);
            var boxList = Box.getBoxWithNumbersList(numberOfPrisoners);
            if(isRandomSimulationCorrect(prisonersList,boxList)) randomSuccess++;
            if(isMethodSimulationCorrect(prisonersList,boxList)) methodSuccess++;

            if(tryPerPercent == 0) {
                System.out.print("#");
                tryPerPercent = numberOfTries/100;
            }
            tryPerPercent--;

        }
        System.out.println("  ");
        System.out.println(" - Success rate random: ");
        System.out.println(randomSuccess*100.0/numberOfTries);
        System.out.println(" - Success rate random: ");
        System.out.println(methodSuccess*100.0/numberOfTries);
    }
}