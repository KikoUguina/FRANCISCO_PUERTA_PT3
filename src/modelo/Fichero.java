package modelo;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Clase que contiene los metodos de exportar a excel, sql y xml.
 * @author francisco.puerta
 *
 */
public class Fichero {
	/**
	 * Metodo que exporta a excel la Jtable que haya en el momento.
	 * @param t
	 * @throws IOException
	 */
    public void exportarExcel(JTable t) throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String ruta = chooser.getSelectedFile().toString().concat(".xls");
            try {
                File archivoXLS = new File(ruta);
                if (archivoXLS.exists()) {
                    archivoXLS.delete();
                }
                archivoXLS.createNewFile();
                Workbook libro = new HSSFWorkbook();
                FileOutputStream archivo = new FileOutputStream(archivoXLS);
                Sheet hoja = libro.createSheet("Mi hoja de trabajo 1");
                hoja.setDisplayGridlines(false);
                for (int f = 0; f < t.getRowCount(); f++) {
                    Row fila = hoja.createRow(f);
                    for (int c = 0; c < t.getColumnCount(); c++) {
                        Cell celda = fila.createCell(c);
                        if (f == 0) {
                            celda.setCellValue(t.getColumnName(c));
                        }
                    }
                }
                int filaInicio = 1;
                for (int f = 0; f < t.getRowCount(); f++) {
                    Row fila = hoja.createRow(filaInicio);
                    filaInicio++;
                    for (int c = 0; c < t.getColumnCount(); c++) {
                        Cell celda = fila.createCell(c);
                        if (t.getValueAt(f, c) instanceof Double) {
                            celda.setCellValue(Double.parseDouble(t.getValueAt(f, c).toString()));
                        } else if (t.getValueAt(f, c) instanceof Float) {
                            celda.setCellValue(Float.parseFloat((String) t.getValueAt(f, c)));
                        } else {
                            celda.setCellValue(String.valueOf(t.getValueAt(f, c)));
                        }
                    }
                }
                libro.write(archivo);
                archivo.close();
                Desktop.getDesktop().open(archivoXLS);
            } catch (IOException | NumberFormatException e) {
                throw e;
            }
        }
    }
    /**
     * Metodo que exporta a SQL los inserts de la JTable que haya en el momento.
     * @param arrayLleno
     */
    public void CrearSQL(ArrayList<City> arrayLleno) {;
    	File archivo = new File("src/Archivos_exportados/MisConsultas.sql");
    	PrintWriter pw = null;
        
        try {
        	pw = new PrintWriter(new FileWriter(archivo, true));
        	for(int i = 0; i < arrayLleno.size(); i++) {
        		pw.println("insert into miTabla values('"
        				+ arrayLleno.get(i).getPais()+"','"
        				+ arrayLleno.get(i).getDistrito()+"','"
        				+ arrayLleno.get(i).getCiudad()+"','"
        				+ arrayLleno.get(i).getContinente()+"','"
        				+ arrayLleno.get(i).getIdioma()+"', "
        				+ arrayLleno.get(i).getPoblacion()+");");
        	}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(pw!=null) {
				pw.close();
			}
		}
    }
    /**
     * Metodo que exporta a xml la Jtable que haya en el momento.
     * @param tb
     * @param FileName
     */
    public void CrearXml(JTable tb, String FileName){
        try{
            String file = FileName;
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Datos");
            doc.appendChild(rootElement);
            
            int i=0;
            
            while (i<tb.getRowCount()){
                int j=0;
                Element rows = doc.createElement("Fila");
                rootElement.appendChild(rows);
                
                Attr attr = doc.createAttribute("id");
                attr.setValue((i+1)+"");
                rows.setAttributeNode(attr);
                
                while (j<tb.getColumnCount()){
                    Element element = doc.createElement(tb.getTableHeader().getColumnModel().getColumn(j).getHeaderValue().toString().trim());
                    element.appendChild(doc.createTextNode(tb.getModel().getValueAt(i,j).toString().trim()));
                    rows.appendChild(element);
                    j++;
                }
                i++;
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            DOMSource source = new DOMSource(doc);
            StreamResult result;
            
            try{
                FileOutputStream fileOutputStream = null;
                
                fileOutputStream = new FileOutputStream(new File("src/Archivos_exportados/"+file+".xml"));
                
                result = new StreamResult(fileOutputStream);
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.transform(source, result);
                try{
                    fileOutputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
            
        }catch (ParserConfigurationException pce){
            pce.printStackTrace();
        } catch (TransformerException te){
            te.printStackTrace();
        }
    }
}
