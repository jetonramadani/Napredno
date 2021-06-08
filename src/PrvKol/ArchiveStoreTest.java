//package PrvKol;
//
//import java.util.*;
//
//public class ArchiveStoreTest {
//    public static void main(String[] args) {
//        ArchiveStore store = new ArchiveStore();
//        Date date = new Date(113, 10, 7);
//        Scanner scanner = new Scanner(System.in);
//        scanner.nextLine();
//        int n = scanner.nextInt();
//        scanner.nextLine();
//        scanner.nextLine();
//        int i;
//        for (i = 0; i < n; ++i) {
//            int id = scanner.nextInt();
//            long days = scanner.nextLong();
//            Date dateToOpen = new Date(date.getTime() + (days * 24 * 60
//                    * 60 * 1000));
//            LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
//            store.archiveItem(lockedArchive, date);
//        }
//        scanner.nextLine();
//        scanner.nextLine();
//        n = scanner.nextInt();
//        scanner.nextLine();
//        scanner.nextLine();
//        for (i = 0; i < n; ++i) {
//            int id = scanner.nextInt();
//            int maxOpen = scanner.nextInt();
//            SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
//            store.archiveItem(specialArchive, date);
//        }
//        scanner.nextLine();
//        scanner.nextLine();
//        while(scanner.hasNext()) {
//            int open = scanner.nextInt();
//            try {
//                store.openItem(open, date);
//            } catch(NonExistingItemException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//        System.out.println(store.getLog());
//    }
//}
//
//// вашиот код овде
//
//
//abstract class Archive{
//    protected int id;
//    protected Date date;
//
//    public Archive(int id) {
//        this.id = id;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
//
//    public int getId() {
//        return id;
//    }
//    public abstract String tryToOpen(Date date);
//}
//class LockedArchive extends Archive {
//    private Date dateToOpen;
//    public LockedArchive(int id, Date dateToOpen) {
//        super(id);
//        this.dateToOpen = dateToOpen;
//    }
//
//    @Override
//    public String tryToOpen(Date date) {
//        if (date.after(dateToOpen)) {
//            return String.format("Item %d opened at %s\n", this.getId(), date);
//        } else {
//            return String.format("Item %d cannot be opened before %s\n", this.getId(), dateToOpen);
//        }
//    }
//}
//
//class SpecialArchive extends Archive{
//    private int maxOpen;
//    private int opened;
//
//    public SpecialArchive(int id, int maxOpen) {
//        super(id);
//        this.maxOpen = maxOpen;
//        this.opened = 0;
//    }
//
//    @Override
//    public String tryToOpen(Date date) {
//        if (this.opened < this.maxOpen) {
//            this.opened++;
//            return String.format("Item %d opened at %s\n", this.getId(), date);
//        } else {
//            return String.format("Item %d cannot be opened more than %s times\n", this.getId(), maxOpen);
//        }
//    }
//}
//class ArchiveStore {
//    private List<Archive> archiveList;
//    private StringBuilder log;
//    public ArchiveStore() {
//        archiveList = new ArrayList<>();
//        log = new StringBuilder();
//    }
//    public void archiveItem(Archive item, Date date) {
//        item.setDate(date);
//        archiveList.add(item);
//        log.append(String.format("Item %d archived at %s\n", item.getId(), date));
//    }
//    public void openItem(int id, Date date) throws NonExistingItemException {
//        Archive archive = archiveList.stream().filter(item -> item.getId() == id).findFirst().orElse(null);
//        if (archive == null)
//            throw new NonExistingItemException(id);
//        log.append(archive.tryToOpen(date));
//    }
//    public String getLog() {
//        return log.toString().replaceAll("GMT", "UTC");
//    }
//}
//class NonExistingItemException extends Throwable {
//    int id;
//
//    public NonExistingItemException(int id) {
//        this.id = id;
//    }
//
//    @Override
//    public String getMessage() {
//        return String.format("Item with id %d doesn't exist", id);
//    }
//}




import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArchiveStoreTest {
    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
        LocalDate date = LocalDate.of(2013, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();

            LocalDate dateToOpen = date.atStartOfDay().plusSeconds(days * 24 * 60 * 60).toLocalDate();
            LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
            store.archiveItem(lockedArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            int maxOpen = scanner.nextInt();
            SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        while(scanner.hasNext()) {
            int open = scanner.nextInt();
            try {
                store.openItem(open, date);
            } catch(NonExistingItemException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(store.getLog());
    }
}


abstract class Archive{
    protected int id;
    protected LocalDate date;

    public Archive(int id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }
    public abstract String tryToOpen(LocalDate date);
}
class LockedArchive extends Archive {
    private LocalDate dateToOpen;
    public LockedArchive(int id, LocalDate dateToOpen) {
        super(id);
        this.dateToOpen = dateToOpen;
    }

    @Override
    public String tryToOpen(LocalDate date) {
        if (date.isAfter(dateToOpen)) {
            return String.format("Item %d opened at %s\n", this.getId(), date);
        } else {
            return String.format("Item %d cannot be opened before %s\n", this.getId(), dateToOpen);
        }
    }
}

class SpecialArchive extends Archive{
    private int maxOpen;
    private int opened;

    public SpecialArchive(int id, int maxOpen) {
        super(id);
        this.maxOpen = maxOpen;
        this.opened = 0;
    }

    @Override
    public String tryToOpen(LocalDate date) {
        if (this.opened < this.maxOpen) {
            this.opened++;
            return String.format("Item %d opened at %s\n", this.getId(), date);
        } else {
            return String.format("Item %d cannot be opened more than %s times\n", this.getId(), maxOpen);
        }
    }
}
class ArchiveStore {
    private List<Archive> archiveList;
    private StringBuilder log;
    public ArchiveStore() {
        archiveList = new ArrayList<>();
        log = new StringBuilder();
    }
    public void archiveItem(Archive item, LocalDate date) {
        item.setDate(date);
        archiveList.add(item);
        log.append(String.format("Item %d archived at %s\n", item.getId(), date));
    }
    public void openItem(int id, LocalDate date) throws NonExistingItemException {
        Archive archive = archiveList.stream().filter(item -> item.getId() == id).findFirst().orElse(null);
        if (archive == null)
            throw new NonExistingItemException(id);
        log.append(archive.tryToOpen(date));
    }
    public String getLog() {
        return log.toString();
    }
}
class NonExistingItemException extends Throwable {
    int id;

    public NonExistingItemException(int id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return String.format("Item with id %d doesn't exist", id);
    }
}