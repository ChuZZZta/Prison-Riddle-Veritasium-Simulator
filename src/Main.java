import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    static class Prisoner {
        Prisoner(int n){
            number = n;
        }

        private final int number;

        public static List<Prisoner> getPrisonerList(int l){
            var prionsersList = new ArrayList<Prisoner>();
            for(int i = 1; i<=l;i++){
                prionsersList.add(new Prisoner(i));
            }
            return prionsersList;
        }

        public int getNumber() {
            return number;
        }
    }

    static class Box{
        Box(int n, int p){
            number = n;
            prisonerNumber = p;
        }
        private final int number;

        private final int prisonerNumber;

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

        public int getNumber() {
            return number;
        }

        public int getPrisonerNumber() {
            return prisonerNumber;
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
        var boxesNumbersToCheckListToCopy = new ArrayList<Integer>();
        for(int ii = 0; ii<prisonerList.size(); ii++){
            boxesNumbersToCheckListToCopy.add(ii);
        }

        for (Prisoner p: prisonerList
             ) {
            var halfLimit = prisonerList.size()/2;
            List<Integer> boxesNumbersToCheck =(ArrayList)boxesNumbersToCheckListToCopy.clone();

            while(halfLimit>0)
            {
                int boxToCheck = getRandomIntFromListAndDeleteIt(boxesNumbersToCheck);
                if(boxList.get(boxToCheck).getPrisonerNumber() == p.getNumber()){
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
            var boxToCheck = p.getNumber();

            while(halfLimit>0)
            {
                if(boxList.get(boxToCheck-1).getPrisonerNumber() == p.getNumber()){
                    break;
                } else {
                    boxToCheck = boxList.get(boxToCheck-1).getPrisonerNumber();
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
        int numberOfTries = 10000000; //numbers of tries
        int numberOfPrisoners = 100; //prisoner amount

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