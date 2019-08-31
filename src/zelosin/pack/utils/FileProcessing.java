package zelosin.pack.utils;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import org.apache.commons.io.FileUtils;
import zelosin.pack.controllers.frames.UsbFrameController;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;


public class FileProcessing {

    private static ProgressBar mProgressBar;
    private static File mTempFile;
    private static ObservableList<String> mFoldersObservableList;
    private static long mLengthOfFolders;
    private static File mDirDestination;
    private static ArrayList<String> mUSBFlashDrivers = new ArrayList<>();
    private static Task<Void> mCopyTask;

    public static void setmDirDestination(String pPath) {
        if (pPath!=null)
            mDirDestination = new File(pPath);
    }

    public static void setProgressBar(ProgressBar progressBar) {
        mProgressBar = progressBar;
    }

    public static void updateUSBConnection(){
        mUSBFlashDrivers.clear();
        File[] paths;
        FileSystemView fsv = FileSystemView.getFileSystemView();
        paths = File.listRoots();
        for(File path:paths)
        {
            if (fsv.getSystemTypeDescription(path)!= null) {
                if (fsv.getSystemTypeDescription(path).contains("USB-накопитель") || fsv.getSystemTypeDescription(path).contains("USB-Drive") || fsv.getSystemTypeDescription(path).contains("Съемный диск")|| fsv.getSystemTypeDescription(path).contains("Съемный носитель")) {
                    mUSBFlashDrivers.add(fsv.getSystemDisplayName(path));
                   // System.out.println(fsv.getSystemTypeDescription(path));
               }
            }
        }
    }

    public static ArrayList<String> getmUSBFlashDrivers() {
        return mUSBFlashDrivers;
    }


    public static Task<Void> getmCopyTask() {
        return mCopyTask;
    }

    private static void createNewTask(){
        mCopyTask = new Task<Void>() {
            File source;
            File fileForRename;
            @Override
            protected Void call() throws Exception {
                for (String s : FileProcessing.mFoldersObservableList) {
                    try {
                        source = new File(GlobalData.getmBaseDir() + "\\" + s);
                        if(source.exists()){
                            fileForRename = new File(mDirDestination + "\\" + s);
                            if(fileForRename.exists()){
                                int i = 1;
                                while(fileForRename.exists()) {
                                   fileForRename = new File(mDirDestination + "\\" + s + " (" +i + ")");
                                   i++;
                               }
                            }
                            FileUtils.copyDirectory(source, fileForRename);
                            updateProgress(FileProcessing.mFoldersObservableList.indexOf(s) + 1, FileProcessing.mFoldersObservableList.size());
                        }
                        else
                            throw new IOException();
                    } catch (IOException e) {
                        updateMessage("Cancelled!");
                        return null;
                    }
                }
                updateMessage("Done!");
                return null;
            }
        };
        mCopyTask.messageProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.equals("Done!")) {
                ((UsbFrameController)GlobalData.mUsbLoader.getFXMLloader().getController()).updateMessage("Копирование завершено.");
                try {
                    Runtime.getRuntime().exec(GlobalData.getmBaseDir()+"removedrive "+mDirDestination);
                } catch (IOException e) {
                }
            }
            else {
                ((UsbFrameController)GlobalData.mUsbLoader.getFXMLloader().getController()).updateMessage("Копирование прервано.");
                updateUSBConnection();
                ((UsbFrameController)GlobalData.mUsbLoader.getFXMLloader().getController()).updateUSBList();
                ((UsbFrameController)GlobalData.mUsbLoader.getFXMLloader().getController()).fillListWithFolders();
            }
        }));
    }

    public static long processingSize(){
        mLengthOfFolders = 0;
        for (String s : FileProcessing.mFoldersObservableList)
            mLengthOfFolders += sizeByPath(Paths.get(GlobalData.getmBaseDir() + s));
        return mLengthOfFolders/1024;
    }

    public static void copyProcessing() {
        mLengthOfFolders = 0;
        if (mDirDestination.toPath().toString() == null) {
            ((UsbFrameController)GlobalData.mUsbLoader.getFXMLloader().getController()).updateMessage("Устройство не выбрано.");
            return;
        }
        if (mFoldersObservableList == null || mFoldersObservableList.size() == 0) {
            ((UsbFrameController)GlobalData.mUsbLoader.getFXMLloader().getController()).updateMessage("Файлы не выбраны.");
            return;
        }
        for (String s : FileProcessing.mFoldersObservableList)
            mLengthOfFolders += sizeByPath(Paths.get(GlobalData.getmBaseDir() + s));

        if (mLengthOfFolders > mDirDestination.getUsableSpace()) {
            ((UsbFrameController)GlobalData.mUsbLoader.getFXMLloader().getController()).updateMessage("Недостаточно места.");
            return;
        } else {
            createNewTask();
            mProgressBar.progressProperty().bind(FileProcessing.getmCopyTask().progressProperty());
            Thread thread = new Thread(mCopyTask);
            thread.setDaemon(true);
            thread.start();
        }
    }

    public static void setmFoldersObservableList(ObservableList<String> mFoldersObservableList) {
        FileProcessing.mFoldersObservableList = mFoldersObservableList;
    }

    public static String[] loadFileFromDir(String pPath){
        mTempFile = new File(pPath);
        return mTempFile.list();
    }

    public static long sizeByPath(Path path) {

        final AtomicLong size = new AtomicLong(0);

        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

                    size.addAndGet(attrs.size());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {

        }
        return size.get();
    }

}










