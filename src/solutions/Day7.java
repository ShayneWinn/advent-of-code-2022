package solutions;

import java.util.ArrayList;

public class Day7 {
    private static class File {
        String name;
        int size;

        public File(String name, int size) {
            this.name = name;
            this.size = size;
        }
    }

    private static class Directory {
        String name;
        Directory superDir;
        ArrayList<File> files;
        ArrayList<Directory> subDirs;

        public Directory(String name, Directory superDir) {
            this.name = name;
            this.superDir = superDir;
            this.files = new ArrayList<File>();
            this.subDirs = new ArrayList<Directory>();
        }

        public boolean contiainsDir(String name){
            for (Directory dir : this.subDirs) {
                if(dir.name.equals(name))
                    return true;
            }
            return false;
        }

        public Directory getSubDirectory(String name){
            for (Directory dir : this.subDirs) {
                if(dir.name.equals(name))
                    return dir;
            }
            return null;
        }
    }

    public static void run() {
        ArrayList<Directory> directories = new ArrayList<>();
        Directory homeDir = new Directory("/", null);
        directories.add(homeDir);
        Directory currentDir = homeDir;

        // GENERATE FILE STRUCTURE

        String[] lines = Common.FileToLines("inputs/d7.input");
        int i = 0;
        while (i < lines.length) {
            String[] tokens = lines[i].split(" ");
            if (!tokens[0].equals("$"))
                System.out.println("d7p1:  ATTEMPTED TO PARSE NON-COMMAND");
            
            switch(tokens[1]){
                case "cd":
                    if(tokens[2].equals("..")){
                        if (currentDir.superDir == null)
                            System.out.println(String.format("d7p1:  SUPER DIR IS NULL; '%s' line %d", currentDir.name, i));
                        currentDir = currentDir.superDir;
                    }
                    else if(currentDir.contiainsDir(tokens[2])){
                        currentDir = currentDir.getSubDirectory(tokens[2]);
                    }
                    else {
                        System.out.println("d7p1:  CD INTO DIRECTORY NOT ADDED '" + tokens[2] + "' line " + Integer.toString(i));
                    }
                    i++;
                    break;

                case "ls":
                    i++;
                    tokens = lines[i].split(" ");
                    while (!tokens[0].equals("$")) {
                        if(tokens[0].equals("dir")){
                            if(currentDir.contiainsDir(tokens[1])){
                                System.out.println("d7p1:  ALREADY ADDED DIRECTORY '" + tokens[1] + "'");
                            }

                            Directory newDir = new Directory(tokens[1], currentDir);
                            directories.add(newDir);
                            currentDir.subDirs.add(newDir);
                        }
                        else{
                            currentDir.files.add(new File(tokens[1], Integer.parseInt(tokens[0])));
                        }

                        i++;
                        if(i >= lines.length)
                            break;
                        tokens = lines[i].split(" ");
                    }
                    break;
            }
        }

        // PARSE FOR FILE SIZES

        int totalSize = 0;
        Directory currentBest = homeDir;
        int maxSize = 70000000;
        int updateSize = 30000000;
        int requiredEmpty = updateSize - (maxSize - DirectorySize(homeDir));

        for(Directory dir : directories){
            int size = DirectorySize(dir);
            if(size <= 100000){
                totalSize += size;
            }
            if(size >= requiredEmpty){
                if(size < DirectorySize(currentBest)){
                    currentBest = dir;
                }
            }
        }

        System.out.println(String.format("d7p1:  Total size of files <= 100000 = %d", totalSize));
        System.out.println(String.format("d7p2:  Size of smallest file to delete = %d", DirectorySize(currentBest)));
    }

    private static int DirectorySize(Directory directory){
        int size = 0;
        for (File file : directory.files) {
            size += file.size;
        }
        for (Directory subDir : directory.subDirs){
            size += DirectorySize(subDir);
        }

        return size;
    }
}
