package net.ericsonj.verilog;

import sun.nio.cs.ext.ExtendedCharsets;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ericson Joseph <ericsonjoseph@comtor.net>
 */
public class VerilogFile {

    public LinkedList<String> memFile;
    public LinkedList<StyleImp> styles;
    public final FileFormat format;
    public String pathname;
    public String charset;

    /**
     * default using charset UTF-8
     *
     * @param pathname file need to be formatted
     * @param format file format instance
     */
    public VerilogFile(String pathname, FileFormat format) {
        this.memFile = new LinkedList<>();
        this.styles = new LinkedList<>();
        this.format = format;
        this.pathname = pathname;
        this.charset = "UTF-8";
        try (FileInputStream fileInputStream = new FileInputStream(pathname);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, charset);
                BufferedReader bufferReader = new BufferedReader(inputStreamReader)) {
            String line;
            while ((line = bufferReader.readLine()) != null) {
                memFile.add(line.trim());
            }
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(VerilogFormat.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VerilogFormat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Using specified charset
     *
     * @param pathname file need to be formatted
     * @param format file format instance
     * @param charset specified charset
     */
    public VerilogFile(String pathname, FileFormat format, String charset) throws UnsupportedEncodingException {
        this.memFile = new LinkedList<>();
        this.styles = new LinkedList<>();
        this.format = format;
        this.pathname = pathname;
        this.charset = charset;
        try (FileInputStream fileInputStream = new FileInputStream(pathname);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, charset);
                BufferedReader bufferReader = new BufferedReader(inputStreamReader)) {
            String line;
            while ((line = bufferReader.readLine()) != null) {
                memFile.add(line.trim());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VerilogFormat.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VerilogFormat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void format() {
        for (StyleImp style : styles) {
            style.applyStyle(format, memFile);
        }
    }

    public void addStyle(StyleImp style) {
        this.styles.add(style);
    }

    public void print() {
        for (String line : memFile) {
            System.out.println(line);
        }
    }

    public void overWrite() {

        try (FileOutputStream fileOutputStream = new FileOutputStream(pathname);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, charset);
                BufferedWriter formatFile = new BufferedWriter(outputStreamWriter)) {
            for (String string : memFile) {
                formatFile.write(string + FileFormat.LF);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(VerilogFormat.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VerilogFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
