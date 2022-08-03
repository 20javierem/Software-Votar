package com.babas.utilities;

import com.babas.App;
import com.babas.utilitiesTables.UtilitiesTables;
import com.formdev.flatlaf.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.swing.JRViewerPanel;
import net.sf.jasperreports.swing.JRViewerToolbar;
import net.sf.jasperreports.view.JasperViewer;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

public class Utilities {
    public static DateFormat formatoFecha=new SimpleDateFormat("yyyy-MM-dd");
    public static DateFormat formatoFechaHora=new SimpleDateFormat("dd/MM/yyyy: h:mm a");
    public static DateFormat formatoHora=new SimpleDateFormat("HH:mm a");
    public static DateFormat año=new SimpleDateFormat("yyyy");
    public static NumberFormat moneda = NumberFormat.getCurrencyInstance();
    public static NumberFormat precio = new DecimalFormat("$#,###.##");
    public static String getFormatoFecha(){
        return "dd/MM/yyyy";
    }
    private static JFrame jFrame;
    private static Propiedades propiedades;

    public static JSpinner.NumberEditor getEditorPrice(JSpinner spinner) {
        return new JSpinner.NumberEditor(spinner, "###,###,###.##");
    }
    public static void setTema(String tema){
        FlatLaf.registerCustomDefaultsSource("com.babas.themes");
        switch (tema){
            case "Ligth":
                FlatIntelliJLaf.setup();
                break;
            case "Oscuro":
                FlatDarkLaf.setup();
                break;
            case "Claro":
                FlatLightLaf.setup();
                break;
            case "Darcula":
                FlatDarculaLaf.setup();
                break;
        }
    }

    public static Propiedades getPropiedades() {
        return propiedades;
    }

    public static void setPropiedades(Propiedades propiedades) {
        Utilities.propiedades = propiedades;
    }

    public static void setJFrame(JFrame jFrame){
        Utilities.jFrame=jFrame;
    }

    public static JFrame getJFrame(){
        return jFrame;
    }

    public static void updateComponents(JComponent parent){
        Font font=parent.getFont();
        parent.updateUI();
        parent.setFont(font);
        if(parent instanceof JTable){
            UtilitiesTables.actualizarTabla((JTable) parent);
        }else if(parent instanceof JMenu){
            for (Component component:((JMenu) parent).getMenuComponents()){
                updateComponents((JComponent) component);
            }
        }else{
            for(Component component:parent.getComponents()){
                if(component instanceof JComponent){
                    updateComponents((JComponent) component);
                }
            }
        }
    }

    public static Date getDate(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR,00);
        calendar.set(Calendar.MINUTE,00);
        calendar.set(Calendar.SECOND,00);
        return calendar.getTime();
    }
    public static boolean precioEsValido(KeyEvent e, String precio){
        int caracter = e.getKeyChar();
        if(caracter==46){
            if(precio.lastIndexOf('.')==-1){
                if(precio.length()>0){
                    return true;
                }
                return false;
            }else{
                return false;
            }
        }else{
            if(caracter >= 48 && caracter <= 57){
                return true;
            }else{
                return false;
            }
        }
    }

    public static void buttonSelected(JToggleButton boton){
        boton.setSelected(true);
    }

    public static void despintarButton(JToggleButton boton){
        boton.setSelected(false);
    }

    public static Date convertLocalTimeToDate(LocalTime time) {
        Instant instant = time.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant();
        return toDate(instant);
    }

    private static Date toDate(Instant instant) {
        BigInteger millis = BigInteger.valueOf(instant.getEpochSecond()).multiply(
                BigInteger.valueOf(1000));
        millis = millis.add(BigInteger.valueOf(instant.getNano()).divide(
                BigInteger.valueOf(1_000_000)));
        return new Date(millis.longValue());
    }

    public static Date localDateToDate(LocalDate dateToConvert) {
        return Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static LocalDate dateToLocalDate(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static String getCodeOfName(String nameProduct){
        nameProduct=nameProduct.trim();
        boolean stade=true;
        String code="";
        if(!nameProduct.isEmpty()){
            int aux;
            do {
                code+=nameProduct.substring(0,1);
                aux=nameProduct.indexOf(" ");
                if(aux!=-1){
                    nameProduct=nameProduct.substring(aux+1);
                }else{
                    stade=false;
                }
            }while (stade);
        }
        return code;
    }

    public static Vector invertirVector(Vector vector){
        Object ventaAUX;
        for(int i=0;i<vector.size()/2;i++){
            ventaAUX=vector.get(i);
            vector.set(i, vector.get(vector.size() - i-1));
            vector.set((vector.size()-i-1), ventaAUX);
        }
        return vector;
    }

    public static Integer calcularaños(Date fecha){
        Calendar hoy=Calendar.getInstance();
        Calendar nacimiento=Calendar.getInstance();
        nacimiento.setTime(fecha);
        int años= hoy.get(Calendar.YEAR)-nacimiento.get(Calendar.YEAR);
        int meses= hoy.get(Calendar.MONTH)-nacimiento.get(Calendar.MONTH);
        int dias= hoy.get(Calendar.DAY_OF_MONTH)-nacimiento.get(Calendar.DAY_OF_MONTH);
        switch (meses){
            case 0:
                if(dias<0){
                    años-=1;
                }
                break;
            default:
                if(meses<0){
                    años-=1;
                }
        }
        return años<0?0:años;
    }

    public static SecretKeySpec getSecretKey(){
        Propiedades propiedades=new Propiedades();
        byte[] salt = "12345678".getBytes();
        int iterationCount = 40000;
        int keyLength = 128;
        try {
            return createSecretKey(propiedades.getKey().toCharArray(),salt,iterationCount,keyLength);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static SecretKeySpec createSecretKey(char[] password, byte[] salt, int iterationCount, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
        SecretKey keyTmp = keyFactory.generateSecret(keySpec);
        return new SecretKeySpec(keyTmp.getEncoded(), "AES");
    }
    public static String encriptar(String contraseña){
        return encrypt(contraseña,getSecretKey());
    }
    public static String desencriptar(String contraseña){
        return decrypt(contraseña,getSecretKey());
    }
    public static String encrypt(String dataToEncrypt, SecretKeySpec key) {
        try {
            Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            pbeCipher.init(Cipher.ENCRYPT_MODE, key);
            AlgorithmParameters parameters = pbeCipher.getParameters();
            IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
            byte[] cryptoText = pbeCipher.doFinal(dataToEncrypt.getBytes("UTF-8"));
            byte[] iv = ivParameterSpec.getIV();
            return base64Encode(iv) + ":" + base64Encode(cryptoText);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error, notifique al administrador");
        }
        return null;
    }
    private static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String decrypt(String string, SecretKeySpec key) {
        try {
            String iv = string.split(":")[0];
            String property = string.split(":")[1];
            Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)));
            return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] base64Decode(String property) throws IOException {
        return Base64.getDecoder().decode(property);
    }

//    public static void generarNotaVenta(DnDTabbedPane tabbedPane, Credentials credentials, Sale sale, Boolean imprimir) {
//        InputStream pathReport = Principal.class.getResourceAsStream("JasperReport/Blank_A4_1.jasper");
//        String logo=Principal.class.getResource("Images/buitre.png").getPath();
//        try {
//            if(pathReport!=null){
//                List<DetailSale> items=new ArrayList<>(sale.getDetailSales());
//                items.add(0, items.get(0));
//                JasperReport report=(JasperReport) JRLoader.loadObject(pathReport);
//                JRBeanArrayDataSource sp=new JRBeanArrayDataSource(items.toArray());
//                Map<String,Object> parameters=new HashMap<>();
//                parameters.put("tipoDocumentoEmpresa","Ruc");
//                parameters.put("rfc","10620205546");
//                parameters.put("direccion",sale.getBranch().getFiscal_address());
//                parameters.put("telefono",sale.getBranch().getPhone());
//                parameters.put("email",sale.getBranch().getEmail());
//                parameters.put("logo",logo);
//                parameters.put("ciudad",sale.getBranch().getUrbanization());
//                parameters.put("numeroticket",String.valueOf(101010+ sale.getId()));
//                parameters.put("subtotal",precio.format(sale.getSub_total()));
//                parameters.put("tipoCambio",moneda.format(credentials.getCompany().getExchanged_rate()));
//                parameters.put("total",moneda.format(sale.getTotal()));
//                parameters.put("importeEnLetras","son muchos soles");
//                parameters.put("fechaEmision",sale.getCreated());
//                parameters.put("message",sale.getBranch().getMessage());
//                parameters.put("nombreCliente",sale.getClient()!=null?sale.getClient().getTrade_name():"--");
//                parameters.put("vendedor",sale.getUser().getFirst_name()+" "+sale.getUser().getLast_name());
//                parameters.put("clienteDni",sale.getClient()!=null?sale.getClient().getNumber_document():"--");
//                parameters.put("formaDePago",sale.isPaymentTransfer()?"Transferencia":"Efectivo");
//                parameters.put("tipoDocumentoCliente","DNI");
//                parameters.put("productos",sp);
//                JasperViewer viewer = getjasperViewer(report,parameters,sp,true);
//                if(viewer!=null){
//                    Integer idVenta=101010+sale.getId();
//                    viewer.setTitle("Venta NR"+idVenta);
//                    if(tabbedPane.indexOfTab(viewer.getTitle())==-1){
//                        tabbedPane.addTab(viewer.getTitle(), viewer.getRootPane());
//                        tabbedPane.setSelectedComponent(viewer.getRootPane());
//                    }
//                    tabbedPane.setSelectedIndex(tabbedPane.indexOfTab(viewer.getTitle()));
//                }else{
//                    Utilities.sendNotification("Error","Ocurrió un error", TrayIcon.MessageType.ERROR);
//                }
//            }else{
//                sendNotification("Error","No se encontró la plantilla", TrayIcon.MessageType.ERROR);
//            }
//        } catch (JRException e) {
//            e.printStackTrace();
//        }
//    }
    public static JasperViewer getjasperViewer(JasperReport report, Map<String, Object> parameters, JRBeanArrayDataSource sp,boolean isExitOnClose){
        try {
            JasperViewer jasperViewer=new JasperViewer(JasperFillManager.fillReport(report,parameters,sp),isExitOnClose);
            JPanel panel= (JPanel) jasperViewer.getRootPane().getContentPane().getComponent(0);
            JRViewer visor= (JRViewer) panel.getComponent(0);
            JRViewerToolbar toolbar= (JRViewerToolbar) visor.getComponent(0);
            toolbar.getInsets().set(2,2,2,2);
            toolbar.setLayout(new FlowLayout(FlowLayout.CENTER,2,3));
            toolbar.setMaximumSize(new Dimension(toolbar.getWidth(),46));
            toolbar.setMinimumSize(new Dimension(toolbar.getWidth(),46));
            toolbar.setPreferredSize(new Dimension(toolbar.getWidth(),46));
            JRViewerPanel jrViewer= (JRViewerPanel) visor.getComponent(1);
            JScrollPane jScrollPane= (JScrollPane) jrViewer.getComponent(0);
            jScrollPane.setBorder(BorderFactory.createEmptyBorder());
            ((JPanel)visor.getComponent(2)).setLayout(new FlowLayout(FlowLayout.CENTER));
            Font font=new Font(new JTextField().getFont().getFontName(),Font.PLAIN,14);
            ((JPanel)visor.getComponent(2)).getComponent(0).setFont(font);
            for (Component component: toolbar.getComponents()){
                if(component instanceof JTextField||component instanceof JComboBox){
                    component.setMaximumSize(new Dimension(component.getMaximumSize().width,40));
                    component.setPreferredSize(new Dimension(component.getMaximumSize().width,40));
                    component.setMinimumSize(new Dimension(component.getMaximumSize().width,40));
                }else{
                    component.setMaximumSize(new Dimension(40,40));
                    component.setPreferredSize(new Dimension(40,40));
                    component.setMinimumSize(new Dimension(40,40));
                }
            }
            JButton mrZoom=(JButton)toolbar.getComponent(14);
            JButton mnZoom=(JButton)toolbar.getComponent(15);
            jScrollPane.addMouseWheelListener(e -> {
               if(e.isControlDown()){
                   if (e.getWheelRotation() < 0) {
                       mrZoom.doClick();
                   } else {
                       mnZoom.doClick();
                   }
               }
            });
            return jasperViewer;
        } catch (JRException e) {
            e.printStackTrace();
        }
        return null;
    }
}
