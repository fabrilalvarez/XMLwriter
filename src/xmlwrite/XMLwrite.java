package xmlwrite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 *
 * @author Fran
 */
public class XMLwrite {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException, XMLStreamException {
        String ruta = "./serial.txt";

        String[] cod = {"p1", "p2", "p3"};
        String[] desc = {"parafusos", "cravos ", "tachas"};
        int[] precio = {3, 4, 5};

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        String rutaxml = "./products.xml";
        try {
            // CODIGO DE PATRI
            fos = new FileOutputStream(ruta);
            oos = new ObjectOutputStream(fos);

            for (int i = 0; i < cod.length; i++) {
                oos.writeObject(new Product(cod[i], desc[i], precio[i]));
            }
            oos.writeObject(null);
            oos.close();
            fos.close();
            // FLUJO XML
            XMLOutputFactory xof = XMLOutputFactory.newInstance();
            XMLStreamWriter xtw = xof.createXMLStreamWriter(new FileWriter(rutaxml));
            // FLUJO LECTURA TXT
            File f = new File(ruta);
            FileInputStream fstream = new FileInputStream(f);
            ObjectInputStream objectStream = new ObjectInputStream(fstream);
            Product product = null;
            //SE EMPIEZA A ESCRIBIR EL DOCUMENTO XML
            xtw.writeStartDocument("utf-8", "1.0");//escribe a declaracion XML con a Version especificada
            xtw.writeStartElement("Productos");//escribe o tag de inicio de un elemento
            //MIENTRAS TXT PRODUCTO NO SEA NULLO ESCRIBE TODO EN FORMATO XML
            while ((product = (Product) objectStream.readObject()) != null) {
                xtw.writeStartElement("Producto");
                xtw.writeAttribute("Codigo", product.getCodigo());
                xtw.writeStartElement("Descripcion");
                xtw.writeCharacters(product.getDescripcion());//escribe o contido do elemento 
                xtw.writeEndElement();
                xtw.writeStartElement("Precio");
                xtw.writeCharacters(String.valueOf(product.getPrecio()));//escribe o contido do elemento 
                xtw.writeEndElement();
                xtw.writeEndElement();
            }
            xtw.writeEndElement();
            xtw.writeEndDocument();
            //CERRAMOS
            xtw.close();
            objectStream.close();
            fstream.close();
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
        }
    }
}
