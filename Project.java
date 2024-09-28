import java.util.*;

class Song {
    String title;
    String artist;
    String type;

    public Song(String title, String artist, String type) {
        this.title = title;
        this.artist = artist;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Song title = " + title + ", Artist=" + artist + "";
    }

}

class Node {
    Song song;
    Node prev;
    Node next;

    public Node(Song song) {
        this.song = song;
        prev = null;
        next = null;
    }
}

class MusicAlbum {
    Node currentSong;
    Node head;
    int size = 0;

    void addSong(Song song) {
        Node newNode = new Node(song);

        if (head == null) {
            // If the list is empty, set the new node as the only node and make it circular
            head = newNode;
            newNode.next = newNode;
            newNode.prev = newNode;
            currentSong = head;
        } else {
            // If the list is not empty, insert the new node at the beginning
            newNode.prev = head.prev;
            newNode.next = head;
            head.prev.next = newNode;
            head.prev = newNode;
            head = newNode;
            currentSong = head;
        }

    }

    String findSong(String type) {
        Node temp = head;
        // int check = 0;

        do {
            if (temp.song.getType() == type) {
                // check++;
                return temp.toString();
            }
            temp = temp.next;
        } while (temp != head);

        return null;
        

    }

    public void play() throws Exception {
        if (currentSong != null) {
            System.out.println("==================================================================");
            System.out.print("Playing " + currentSong.song.title + " by " + currentSong.song.artist);

            for (int i = 0; i < 5; i++) {
                System.out.print(".");
                Thread.sleep(1000);
            }
            System.out.println();
            System.out.println("==================================================================");
        } else {
            System.out.println("No song to play");
        }
    }

    public void pause() throws Exception {
        if (currentSong != null) {
            System.out.println("==================================================================");

            System.out.println("Pausing: " + currentSong.song.title);
            Thread.sleep(2000);
            System.out.println();
            System.out.println("==================================================================");

        } else {
            System.out.println("No Song is Playing..");
        }

    }

    // public void skip() {
    // if (currentSong != null && currentSong.next != null) {
    // currentSong = currentSong.next;
    // System.out.println("Skipping to: " + currentSong.song.title);
    // }
    // }

    void moveToPreviousSong() {

        currentSong = currentSong.prev;
        System.out.print("Going back to " + currentSong.song.getTitle());

        try {
            for (int i = 0; i < 2; i++) {
                System.out.print(".");
                Thread.sleep(1000);
            }
        } catch (Exception e) {
        }

    }

    void display() {
        Node temp = head;
        do {
            System.out.println(
                    "Song: " + temp.song.title + "\t||  Artist: " + temp.song.artist + "\t||  Album: " + temp.song.type);
            temp = temp.next;
        } while (temp != head);
    }

    void removeCurrentSong() {
        if (currentSong != null) {
            Node prevNode = currentSong.prev;
            Node nextNode = currentSong.next;

            prevNode.next = nextNode;
            nextNode.prev = prevNode;

            if (currentSong == head) {
                head = nextNode;

            }

            System.out.println("Removed : " + currentSong.song.title);
            currentSong = nextNode;

        }
    }

    void moveToStart() {
        currentSong = head;

        System.out.println("Moved to the Start of Playlist !!");

    }

    void moveToNextSong() {
        if (currentSong != null) {
            currentSong = currentSong.next;

            System.out.println("Moved to the Next Song: " + currentSong.song.getTitle());

        } else {
            System.out.println("There is no song available");
        }
    }

    void removeSong(String s) {

        if (head.song.title.equalsIgnoreCase(s) && head.prev == head) {
            head = null;
        } else if (head.song.title.equalsIgnoreCase(s)) {
            Node prevNode = head.prev;

            if (currentSong == head) {
                currentSong = head.next;
            }
            head.prev.next = head.next;
            head.prev = null;

            head = head.next;
            head.prev.next = null;
            head.prev = prevNode;

            System.out.println("Song: " + s + " has been deleted !!");

        } else {
            Node temp = head.next;

            while (!temp.song.title.equalsIgnoreCase(s) && temp != head) {
                temp = temp.next;
            }

            if (temp != head) {

                if (currentSong.song.title.equalsIgnoreCase(s)) {
                    currentSong = currentSong.next;
                }
                temp.prev.next = temp.next;

                temp.next.prev = temp.prev;

                temp.prev = null;
                temp.next = null; 
            System.out.println("Song: " + s + " has been deleted !!");


            } else {
                System.out.println("Not found the song with title: " + s);
            }
        }
    }

    void shuffle() {
        if (head == null) {
            System.out.println("The playlist is empty.");
            return;
        }

        List<Node> nodeList = new ArrayList<>();
        Node temp = head;

        do {
            nodeList.add(temp);
            temp = temp.next;
        } while (temp != head);

        Collections.shuffle(nodeList); // Shuffle the list

        head = nodeList.get(0);
        head.prev = nodeList.get(nodeList.size() - 1);
        nodeList.get(0).next = head;
        nodeList.get(nodeList.size() - 1).next = nodeList.get(0);

        for (int i = 1; i < nodeList.size(); i++) {
            nodeList.get(i).prev = nodeList.get(i - 1);
            nodeList.get(i - 1).next = nodeList.get(i);
        }

        currentSong = head;
        System.out.println("Playlist shuffled:");

    }

    List<Song> getSong() {
        List get = new ArrayList<>();

        Node temp = head;
        ;
        do {
            get.add(temp.song);
            temp = temp.next;
        } while (temp != head);

        return get;
    }
}

class Playlist {
    String name;
    MusicAlbum music = new MusicAlbum();

    public Playlist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    void addToPlaylist(Song song) {

        music.addSong(song);
        // System.out.println("Song " + song.getTitle() + "by Artist: " +
        // song.getArtist()
        // + " has been added successfully to the playlist !");

    }

    void removeFromPlaylist(String s) {

        music.removeSong(s);
       
    }

    void removeCurrentSongFromPlaylist() {
        music.removeCurrentSong();
    }

    void shufflePlaylist() {

        music.shuffle();

    }

    void displayPlayList() {
        music.display();
    }

    

    Playlist displayByArtist(String artistName, LinkedList<Playlist> playlists) {
        Playlist artist = new Playlist(artistName.toUpperCase() + " HITS");
        boolean found = false; // To check if the artist is found in any playlist

        for (Playlist playlist : playlists) {
            List<Song> songsInPlaylist = playlist.getSongs();

            for (Song song : songsInPlaylist) {
                if (song.getArtist().equalsIgnoreCase(artistName)) {
                    artist.addToPlaylist(song);
                }
            }

        }

        return artist;

    }

    void playSong() throws Exception {
        music.play();
    }

    void pauseSong() throws Exception {
        music.pause();
    }

    void goToStart() {
        music.moveToStart();
    }

    void goToPrev() {
        music.moveToPreviousSong();
    }

    void goToNext() {
        music.moveToNextSong();
    }

    List<Song> getSongs() {

        List<Song> list = music.getSong();
        return list;
    }

}

class Project {
    public static void main(String[] args) throws Exception {

        System.out.println();
        System.out.println("\t\t\t\t -----  WELCOME TO THE MUSIC PLAYER --------");
        Scanner sc = new Scanner(System.in);
        LoginRegister lr = new LoginRegister();
        UserInterface ui = new UserInterface();

        int pref;
        do {
            System.out.println();
            System.out.println("****************************************");
            System.out.println("Enter 1 ~~> Register");
            System.out.println("Enter 2 ~~> Login");
            System.out.println("Enter 3 ~~> Exit");
            System.out.println("****************************************");

            System.out.println("Enter choice: ");
            pref = sc.nextInt();

            switch (pref) {
                case 1:

                    if (lr.register()) {
                        System.out.println();

                        ui.UI();

                    }
                    break;

                case 2:
                    if (lr.login() != null) {
                        System.out.println("Login successful. Welcome, user");

                        ui.UI();
                    }

                    break;

                case 3:
                    System.out.println("Existing System");

            }
        } while (pref != 3);

    }
}

class User {
    String username;
    String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}

class LoginRegister {
    Scanner sc = new Scanner(System.in);
    ArrayList<User> users = new ArrayList<>();

    boolean register() {

        System.out.println("Enter username: ");
        String user = sc.nextLine();
        User u = null;
        boolean check;

        Iterator<User> itr = users.iterator();
        while (itr.hasNext()) {

            if (itr.next().username.equalsIgnoreCase(user)) {
                System.out.println("Username already exits..!!");
                System.out.println();
                System.out.println("Please login....");
                System.out.println();
                return false;
            }
        }

        System.out.println("Enter password: ");
        String pass = sc.nextLine();

        User newUser = new User(user, pass);
        users.add(newUser);

        System.out.println();
        System.out.println("Registered successfully !!");

        return true;

    }

    User login() {
        User u = null;

        boolean check = false;

        System.out.println("Enter username: ");
        String user = sc.nextLine();

        Iterator<User> it = users.iterator();

        while (it.hasNext()) {
            u = it.next();
            if (u.getUsername().equals(user)) {
                System.out.println("Enter password: ");
                String pass = sc.nextLine();

                if (!u.getPassword().equals(pass)) {
                    System.out.println("Wrong password !");
                    System.out.println();
                    check = false;
                    login();
                    
                    break;
                }else{

                check = true;

                break;}
            }
        }

        if (!check) {

            System.out.println("Wrong userName.Please try again..");
            System.out.println();
            return login();

        } else {
            return u;
        }
    }

}

class UserInterface {
    void UI() throws Exception {

        LinkedList<Playlist> list = new LinkedList<>();
        Scanner sc = new Scanner(System.in);
        Playlist play1 = new Playlist("TOP HITS");
        list.add(play1);

        Playlist play2 = new Playlist("BOLLYWOOD TOP HINDI");
        list.add(play2);

        Playlist play3 = new Playlist("TODAY'S TRENDING");
        list.add(play3);

        play1.addToPlaylist(new Song("Garmi song      ", "Neha Kakkar", "Street Dancer 3D"));
        play1.addToPlaylist(new Song("Kesariya        ", "Arjit Singh", "Brahmastra"));
        play1.addToPlaylist(new Song("Kala Chashma    ", "Neha Kakkar", "Baar Baar Dekho"));
        play1.addToPlaylist(new Song("Tere Pyaar Mein ", "Arjit Singh", "Tu Jhoothi Mein Makkar"));
        play1.addToPlaylist(new Song("Jugnu           ", "Badshah", "Jugnu"));

        play2.addToPlaylist(new Song("Badri Ki Dulhania", "Neha Kakkar", "Badrinath Ki Dulhania"));
        play2.addToPlaylist(new Song("The breakup Song ", "Arjit Singh", "Ae Dil Hai Mushkil"));
        play2.addToPlaylist(new Song("London Thumakda  ", "Neha Kakkar", "Queen"));
        play2.addToPlaylist(new Song("Lets Nacho       ", "Badshah", "kapoor and Sons"));

        play3.addToPlaylist(new Song("Chaleya    ", "Arjit Singh", "Jawan"));
        play3.addToPlaylist(new Song("Aankh Marey", "Neha Kakkar", "Simmba"));
        play3.addToPlaylist(new Song("O Bedardeya", "Arjit Singh", "Tu Jhoothi Mein Makkar"));
        play3.addToPlaylist(new Song("Tareefan   ", "Badshah", "Veere Di Wedding"));

        Playlist artist1 = new Playlist("NEHA KAKKAR HITS");
        artist1 = play2.displayByArtist("Neha Kakkar", list);
        artist1.addToPlaylist(new Song("Coca Cola        ", "Neha Kakkar", "Luka Chuppi"));
        list.add(artist1);

        Playlist artist2 = new Playlist("ARJIT SINGH HITS");
        artist2 = play1.displayByArtist("Arjit Singh", list);
        list.add(artist2);

        Playlist artist3 = new Playlist("BADSHAH HITS");
        artist3 = play1.displayByArtist("Badshah", list);
        list.add(artist3);

        int ch;
        do {
            System.out.println("****************************************");
            System.out.println("1. Display All Playlist");
            System.out.println("2. Display Playlist Of Artist");
            System.out.println("3. Create playlist");
            System.out.println("4. Listen Songs");
            System.out.println("5. Exit");
            System.out.println("****************************************");
            System.out.println("Enter your choice: ");
            ch = sc.nextInt();

            switch (ch) {

                case 1:

                    System.out.println();
                    System.out.println("Playlists Available: ");
                    System.out.println();

                    for (Playlist playlist : list) {
                        System.out.println("~~" + playlist.getName() + "~~");
                        System.out.println("______________________________");
                        System.out.println();
                        playlist.displayPlayList();
                        System.out.println();
                    }

                    break;

                case 2:

                    System.out.println();
                    System.out.println("Playlists Available: ");
                    System.out.println();

                    System.out.println("~~" + artist1.getName() + "~~\n");
                    artist1.displayPlayList();
                    System.out.println();

                    System.out.println("~~" + artist2.getName() + "~~\n");
                    artist2.displayPlayList();
                    System.out.println();

                    System.out.println("~~" + artist3.getName() + "~~\n");
                    artist3.displayPlayList();
                    System.out.println();

                    break;

                case 3:
                    System.out.println("Enter name of playlist: ");
                    sc.nextLine();
                    String userPlaylist = sc.nextLine();

                    Playlist playlist = new Playlist(userPlaylist);
                    list.add(playlist);
                    System.out.println();

                    System.out.println("Select from below options: ");

                    while (true) {
                        System.out.println("1 ~~~~> Add song");
                        System.out.println("2 ~~~~> Remove song");
                        System.out.println();
                        System.out.println("Enter your choice: ");

                        int choice = sc.nextInt();

                        switch (choice) {
                            case 1:
                                System.out.println("Enter Song title : ");
                                sc.nextLine();
                                String title = sc.nextLine();

                                System.out.println("Enter Song Artist: ");
                                String artist = sc.nextLine();

                                System.out.println("enter Song Album : ");
                                String album = sc.nextLine();

                                System.out.println("**************************************************");

                                playlist.addToPlaylist(new Song(title, artist, album));
                                System.out.println("~~" + playlist.getName() + "~~");
                                System.out.println("");
                                playlist.displayPlayList();

                                break;

                            case 2:
                                System.out.println("Enter Song title : ");
                                sc.nextLine();
                                String titletoRemove = sc.nextLine();
                    

                                playlist.removeFromPlaylist(titletoRemove);

                                System.out.println();
                                System.out.println("**************************************************");
                                System.out.println("~~" + playlist.getName() + "~~");
                                System.out.println();
                                playlist.displayPlayList();

                                break;

                            default:
                                System.out.println("Enter valid choice (1-2): ");
                        }

                        System.out.println();
                        System.out.println("Do you want to add or remove songs? (yes/no): ");
                        String ans = sc.nextLine();
                        if (ans.equalsIgnoreCase("no") || ans.equalsIgnoreCase("n")) {
                            break;
                        }
                    }

                    break;

                case 4:
                    System.out.println();
                    System.out.println("=========================");
                    System.out.println("Available playlist: ");
                    System.out.println("=========================");

                    for (Playlist l : list) {
                        System.out.println("---->" + l.getName());

                    }
            
                    sc.nextLine();
                    System.out.println();
                    System.out.println("Select the playlist name you want to play: ");

                    String playName = sc.nextLine();
                    boolean flag = false;
                    for (Playlist check : list) {
                        if (check.getName().equalsIgnoreCase(playName)) {
                            flag = true;

                            while (true) {

                                System.out.println();
                                System.out.println("Please select from below options: ");
                                System.out.println();
                                System.out.println("1----> Play Song");
                                System.out.println("2----> Pause Song");
                                System.out.println("3----> Move to Start of the Playlist ");
                                System.out.println("4----> Play Next Song");
                                System.out.println("5----> Play Previous Song");
                                System.out.println("6----> Shuffle the Playlist");
                                System.out.println("7----> Exit");


                                int temp = sc.nextInt();
                                if (temp == 1) {
                                    check.playSong();
                                } else if (temp == 2) {
                                    check.pauseSong();
                                } else if (temp == 3) {
                                    check.goToStart();
                                    check.playSong();

                                } else if (temp == 4) {
                                    check.goToNext();
                                    check.playSong();

                                } else if (temp == 5) {
                                    check.goToPrev();
                                    System.out.println();
                                    check.playSong();

                                } else if (temp == 6) {
                                    check.shufflePlaylist();
                                    check.displayPlayList();
                                } else if (temp == 7) {
                                    break;
                                } else {
                                    System.out.println("Enter valid choice.");
                                }

                            }

                        }
                    }
                    if (!flag) {
                        System.out.println("No playlist with " + playName + " found ");
                    }
                    break;

                case 5:
                    break;
            }

        } while (ch!=5);

}
}