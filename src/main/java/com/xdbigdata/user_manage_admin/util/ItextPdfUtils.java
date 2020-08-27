package com.xdbigdata.user_manage_admin.util;

import com.lowagie.text.pdf.BaseFont;
import com.xdbigdata.framework.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;

/**
 * Itext pdf utils<br><br>
 * <b>1.2.0: </b>Add incoming output stream
 *
 * @author lshaci
 * @since 0.0.6
 * @version 1.2.0
 */
@Slf4j
public class ItextPdfUtils {

    /**
     * simsun font name
     */
    private final static String SIMSUN_NAME = "simsun.ttc";

    /**
     * font path
     */
    private final static String FONT_PATH = "fonts/";

    /**
     * Export PDF with html string, use the default fontPath(./font) <br>
     * The html freemarker template need set body style,<br>
     * 				For example: <b>&lt;body style = "font-family: SimSun;"&gt;</b> <br>
     * 				<b>The sample code: </b><br><br>
     * 				Map&lt;String, Object&gt; data = new HashMap&lt;&gt;();<br>
     * 				String htmlStr = FreemarkerUtils.build(Test.class, "/pdf").setTemplate("test.ftl").generate(data);<br>
     * 				ByteArrayOutputStream pdfOs = ItextPdfUtils.export(htmlStr);<br><br>
     * 				resp.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("测试", "UTF-8") + ".pdf");<br>
     * 				resp.setContentType("application/pdf");<br><br>
     * 				ServletOutputStream outputStream = resp.getOutputStream();<br>
     * 				outputStream.write(pdfOs.toByteArray());<br>
     * @param htmlStr html string
     * @return the pdf ByteArrayOutputStream
     */
    public static ByteArrayOutputStream export(String htmlStr) {
        return export(htmlStr, "./font");
    }

    /**
     * Export PDF with html string, use the default fontPath(./font) <br>
     * The html freemarker template need set body style,<br>
     * 				For example: <b>&lt;body style = "font-family: SimSun;"&gt;</b> <br>
     * 				<b>The sample code: </b><br><br>
     * 				Map&lt;String, Object&gt; data = new HashMap&lt;&gt;();<br>
     * 				resp.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("测试", "UTF-8") + ".pdf");<br>
     * 				resp.setContentType("application/pdf");<br><br>
     * 				String htmlStr = FreemarkerUtils.build(Test.class, "/pdf").setTemplate("test.ftl").generate(data);<br>
     * 				ItextPdfUtils.export(htmlStr, resp.getOutputStream());<br><br>
     * @param htmlStr html string
     * @param os the output stream
     */
    public static void export(String htmlStr, OutputStream os) {
        export(htmlStr, "./font", os);
    }

    /**
     * Export PDF with html string <br>
     * The html freemarker template need set body style,<br>
     * 				For example: <b>&lt;body style = "font-family: SimSun;"&gt;</b> <br>
     * 				<b>The sample code: </b><br><br>
     * 				Map&lt;String, Object&gt; data = new HashMap&lt;&gt;();<br>
     * 				String htmlStr = FreemarkerUtils.build(Test.class, "/pdf").setTemplate("test.ftl").generate(data);<br>
     * 				ByteArrayOutputStream pdfOs = ItextPdfUtils.export(htmlStr, fontPath);<br><br>
     * 				resp.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("测试", "UTF-8") + ".pdf");<br>
     * 				resp.setContentType("application/pdf");<br><br>
     * 				ServletOutputStream outputStream = resp.getOutputStream();<br>
     * 				outputStream.write(pdfOs.toByteArray());<br>
     * @param htmlStr html string
     * @param fontPath Save the file path for the font.
     * @return the pdf ByteArrayOutputStream
     */
    public static ByteArrayOutputStream export(String htmlStr, String fontPath) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        export(htmlStr, fontPath, os);
        return os;
    }

    /**
     * Export PDF with html string <br>
     * The html freemarker template need set body style,<br>
     * 				For example: <b>&lt;body style = "font-family: SimSun;"&gt;</b> <br>
     * 				<b>The sample code: </b><br><br>
     * 				Map&lt;String, Object&gt; data = new HashMap&lt;&gt;();<br>
     * 				String htmlStr = FreemarkerUtils.build(Test.class, "/pdf").setTemplate("test.ftl").generate(data);<br>
     * 				resp.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("测试", "UTF-8") + ".pdf");<br>
     * 				resp.setContentType("application/pdf");<br><br>
     * 				ItextPdfUtils.export(htmlStr, fontPath, resp.getOutputStream());<br><br>
     * @param htmlStr html string
     * @param fontPath Save the file path for the font
     * @param os the output stream
     */
    public static void export(String htmlStr, String fontPath, OutputStream os) {
        try (
                OutputStream os_ = os;
        ) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlStr);

            // Solve the problem of Chinese support.
            ITextFontResolver fontResolver = renderer.getFontResolver();
            String sysPath = createSimsunFont(fontPath);
            if("linux".equals(getCurrentOperatingSystem())){
                fontResolver.addFont("\\static\\font\\simsun.ttc", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            }else{
                fontResolver.addFont("\\static\\font\\simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            }

            renderer.layout();
            renderer.createPDF(os_, true);
        } catch (Exception e) {
            log.error("Error exporting PDF!", e);
            throw new BaseException("Error exporting PDF!", e);
        }
    }

    /**
     * Create simsun font
     *
     * @param fontPath Save the file path for the font.
     * @return simsun font file path
     */
    private static String createSimsunFont(String fontPath) {
        String sysPath = fontPath + File.separator + SIMSUN_NAME;
        File fontFile = new File(sysPath);

        if (!fontFile.exists()) {
            log.info("Simsun font not exist. Create it.");
            String simsunPath = FONT_PATH + SIMSUN_NAME;
            log.info("The simsun font path is: " + simsunPath);
            try (
                    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(simsunPath);
            ) {
                fontFile.getParentFile().mkdirs();
                OutputStream fontFileOs = new FileOutputStream(fontFile);
                byte[] buffer = new byte[4096];
                int ch;
                while ((ch = stream.read(buffer)) != -1) {
                    fontFileOs.write(buffer, 0, ch);
                }
                fontFileOs.flush();
                fontFileOs.close();
            }catch (Exception e) {
                log.error("Create font failure.", e);
                throw new BaseException("Create font failure.", e);
            }
        }
        return sysPath;
    }

    /**
     * Get current operating system
     *
     * @return operating system name
     */
    private static String getCurrentOperatingSystem(){
        return System.getProperty("os.name").toLowerCase();
    }

}

