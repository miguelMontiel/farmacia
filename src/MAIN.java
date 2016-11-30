import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

class MAIN extends JFrame implements ActionListener
{    
    static String bd = "Farmacia";
    static String login = "root";
    static String password = "lucia";
    static String url = "jdbc:mysql://localhost/" + bd;
    
    String getArticulo, getImage;
    Boolean getINAPAM = false;
    Object getFarmaceutica, getMarca, getCosto;
    int confirmar;
    String[] nombreColumnas = {"ARTICULO", "FARMACEUTICA", "MARCA", "COSTO", "INVENTARIO", "INAPAM", "IMAGEN"};
    Object[][] celdas = {{"1", "2", "3", "4", "5", "6", "7"}};
    String[] marca = {"Bimbo", "Barcel", "Coca Cola", "Danone", "Kellog's"}; 
    String[] farmaceutica = {"Solido", "Semi-Solido", "Liquido", "Gaseoso"}; 
    
    JFrame jframe;
    BorderLayout borderlayout, borderlayout1, borderlayout2;
    JTabbedPane tabbedpane;
    JPanel jpanelbuscar, jpanelbuscar1, jpanelbuscar2, jpanelbuscar3, jpanelnuevo, jpanelnuevo1, jpanelnuevo2, jpanelnuevo3, jpanelnuevo4, jpanelnuevo5, jpanelnuevo6, jpanelnuevo7, jpaneltodo, jpaneltiempo, jpanelconfirmacion, jpanelconfirmacion1, jpanelrecibo, jpanelrecibo1, jpanelrecibo2, jpanelrecibo3, jpanelrecibo4, jpanelrecibo5, jpanelrecibo6;
    DefaultTableModel defaulttablemodel;
    DefaultListModel defaultlistmodel;
    JTable jtable;
    JScrollPane jscrollpane;
    ImageIcon BuscarPNG, ListaPNG, TodosPNG;
    JList jlist;
    Date hora;
    
    JTextField jtextfield1, jtextfield2, jtextfield3;
    JComboBox jcombobox1, jcombobox2;
    JSpinner jspinner;
    JCheckBox jcheckbox1, jcheckbox2;
    JButton jbutton1, jbutton2, jbutton3, jbutton4, jbutton5, jbutton6;
    JFileChooser jfilechooser;
    
    MAIN() throws Exception
    {
        jframe = new JFrame();
        borderlayout = new BorderLayout();
        borderlayout1 = new BorderLayout();
        borderlayout2 = new BorderLayout();
        tabbedpane = new JTabbedPane();
        jpanelbuscar = new JPanel();
        jpanelbuscar1 = new JPanel();
        jpanelbuscar2 = new JPanel();
        jpanelbuscar2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createDashedBorder(Color.BLACK), "Carrito de compra: "));
        jpanelbuscar3 = new JPanel();
        jpanelnuevo = new JPanel();
        jpanelnuevo1 = new JPanel();
        jpanelnuevo2 = new JPanel();
        jpanelnuevo3 = new JPanel();
        jpanelnuevo4 = new JPanel();
        jpanelnuevo5 = new JPanel();
        jpanelnuevo6 = new JPanel();
        jpanelnuevo6.setBorder(BorderFactory.createTitledBorder(BorderFactory.createDashedBorder(Color.BLACK), "En caso de ser Medicamento: "));
        jpanelnuevo6.setVisible(false);
        jpanelnuevo7 = new JPanel();
        jpaneltodo = new JPanel();
        jpaneltiempo = new JPanel();
        jpanelconfirmacion = new JPanel();
        jpanelconfirmacion1 = new JPanel();
        jpanelrecibo = new JPanel();
        jpanelrecibo1 = new JPanel();
        jpanelrecibo2 = new JPanel();
        jpanelrecibo3 = new JPanel();
        jpanelrecibo4 = new JPanel();
        jpanelrecibo5 = new JPanel();
        jpanelrecibo6 = new JPanel();
        defaulttablemodel = new DefaultTableModel(celdas, nombreColumnas);
        defaultlistmodel = new DefaultListModel();
        jtable = new JTable(defaulttablemodel);
        jscrollpane = new JScrollPane(jtable);
        jscrollpane.setSize(600, 250);
        BuscarPNG = createImageIcon("Buscar.png");
        ListaPNG = createImageIcon("Lista.png");
        TodosPNG = createImageIcon("Todos.png");
        jlist = new JList(defaultlistmodel);
        jlist.setBackground(new Color(0,0,0,0));
        hora = new Date();
        
        BufferedImage imagen;
        
        jtextfield1 = new JTextField(20);
        jtextfield2 = new JTextField(20);
        jtextfield3 = new JTextField(20);
        jtextfield3.setEditable(false);
        jcombobox1 = new JComboBox(marca);
        jcombobox2 = new JComboBox(farmaceutica);
        jspinner = new JSpinner();
        Component spinnerlength = jspinner.getEditor();
        JFormattedTextField jformattedtextfield = ((JSpinner.DefaultEditor) spinnerlength).getTextField();
        jformattedtextfield.setColumns(5);
        jcheckbox1 = new JCheckBox();
        jcheckbox1.setText("INAPAM");
        jcheckbox2 = new JCheckBox();
        jcheckbox2.setText("Medicamento?");
        jbutton1 = new JButton("Buscar");
        jbutton2 = new JButton("Buscar...");
        jbutton3 = new JButton("Ingresar Producto");
        jbutton4 = new JButton("Aceptar");
        jbutton5 = new JButton("Cancelar");
        jbutton6 = new JButton("Imprimir");
        jfilechooser = new JFileChooser();
        
        jbutton1.addActionListener(this);
        jbutton2.addActionListener(this);
        jbutton3.addActionListener(this);
        jbutton4.addActionListener(this);
        jbutton5.addActionListener(this);
        jcheckbox2.addActionListener(this);
        
        setLayout(borderlayout);
        add(tabbedpane);
        
        setIcon();
        Buscar();
        Ingresar();
        Productos();
        lookandFeel();
        Consultar();
    }
    
    void Buscar()
    {           
        tabbedpane.addTab("Buscar", BuscarPNG, jpanelbuscar, "Aqui se buscan los productos que se van a comprar.");
        jpanelbuscar.add(jpanelbuscar1); jpanelbuscar.add(jpanelbuscar2);
        jpanelbuscar1.setLayout(new GridLayout(5, 3, 10, 10));
        jpanelbuscar2.setLayout(new BorderLayout());
        
        jpanelbuscar1.add(new JLabel(" "));
        jpanelbuscar1.add(new JLabel("Introduzca el nombre del articulo a buscar: "));
        jpanelbuscar1.add(jtextfield1);
	jpanelbuscar1.add(jbutton1);
        jpanelbuscar1.add(new JLabel(" "));
        
        jpanelbuscar2.add(jlist, BorderLayout.CENTER);
        jpanelbuscar2.add(jpanelbuscar3, BorderLayout.SOUTH);
        jpanelbuscar3.add(jbutton4);
        jpanelbuscar3.add(jbutton5);
    }
    
    void Ingresar()
    {
        tabbedpane.addTab("Ingresar Nuevo Producto", ListaPNG, jpanelnuevo, "Aqui se van a ingresar los nuevos productos a la base de datos");
        jpanelnuevo.add(jpanelnuevo1); jpanelnuevo.add(jpanelnuevo2); jpanelnuevo.add(jpanelnuevo3); jpanelnuevo.add(jpanelnuevo4); jpanelnuevo.add(jpanelnuevo5); jpanelnuevo.add(jpanelnuevo6); jpanelnuevo.add(jpanelnuevo7); 
        
        jpanelnuevo1.add(new JLabel("Producto: ")); jpanelnuevo1.add(jtextfield2);
        jpanelnuevo2.add(new JLabel("Marca: ")); jpanelnuevo2.add(jcombobox1);
        jpanelnuevo3.add(new JLabel("Imagen: ")); jpanelnuevo3.add(jbutton2); jpanelnuevo3.add(jtextfield3);
        jpanelnuevo4.add(new JLabel("Precio: ")); jpanelnuevo4.add(jspinner); 
        jpanelnuevo5.add(jcheckbox1); jpanelnuevo5.add(jcheckbox2);
        jpanelnuevo6.add(new JLabel("Farmaceutica: ")); jpanelnuevo6.add(jcombobox2); 
        jpanelnuevo7.add(jbutton3);
    }
    
    void Productos()
    {
        tabbedpane.addTab("Lista de Productos", TodosPNG, jpaneltodo, "Aqui se muestran todos los productos ingresados.");
        jpaneltodo.setLayout(new BorderLayout());
        jpaneltodo.add(jscrollpane, BorderLayout.CENTER);
    }
    
    void setIcon()
    {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
    }
    
    void Recibo()
    {
        jframe.setTitle("Recibo");
        jframe.setSize(250, 400);
        jframe.setVisible(true);
        jframe.add(jpanelrecibo);
        jpanelrecibo.add(jpanelrecibo1); jpanelrecibo.add(jpanelrecibo2); jpanelrecibo.add(jpanelrecibo3); jpanelrecibo.add(jlist); jpanelrecibo.add(jpanelrecibo4); jpanelrecibo.add(jpanelrecibo5); jpanelrecibo.add(jpanelrecibo6);
        jpanelrecibo1.add(new JLabel("Comprado en Farmaceishon, "));
        jpanelrecibo2.add(new JLabel("ubicado en Santa Fe, "));
        jpanelrecibo3.add(new JLabel("Beverly Hills."));
        defaultlistmodel.addElement(jtextfield1.getText());
        jpanelrecibo4.add(new JLabel("Hora de compra: "));
        jpanelrecibo5.add(new JLabel(hora.toString()));
        jpanelrecibo6.add(jbutton6);
    }
    
    void lookandFeel()
    {
        try 
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } 
        catch (UnsupportedLookAndFeelException ex) 
        {
            ex.printStackTrace();
        } 
        catch (IllegalAccessException ex) 
        {
            ex.printStackTrace();
        } 
        catch (InstantiationException ex) 
        {
            ex.printStackTrace();
        } 
        catch (ClassNotFoundException ex) 
        {
            ex.printStackTrace();
        }
        UIManager.put("swing.boldMetal", Boolean.FALSE);
    }

    protected static ImageIcon createImageIcon(String path) 
    {
        java.net.URL imgURL = MAIN.class.getResource(path);
        if (imgURL != null) 
        {
            return new ImageIcon(imgURL);
        } 
        else     
        {
            System.err.println("No se encontro el archivo: " + path);
            return null;
        }
    }
    
    void Imagen()
    {
        BufferedImage imagen = null;
        jfilechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        File file = jfilechooser.getSelectedFile();
        getImage = file.getName();
        jtextfield3.setText(getImage);
         try 
         {
            String fileName = file.getCanonicalPath();
            if (!fileName.endsWith(".png")) 
            {
                file = new File(fileName + ".png");
            }
            ImageIO.write(imagen, "png", file);
        } 
         catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == jbutton1)
        {
            if(jtextfield1.getText().equals("Gaby"))
            {
                JOptionPane.showMessageDialog(null, "<3");
            }
            else if(jtextfield1.getText().equals(""))
            {
            }
            else
            {
                confirmar = JOptionPane.showConfirmDialog(null, "Aqui van a ir los detalles del producto", "Confirmar Producto", JOptionPane.YES_NO_OPTION, WIDTH, ListaPNG);
                if(confirmar == JOptionPane.YES_OPTION)
                {
                    defaultlistmodel.addElement(jtextfield1.getText());
                    jtextfield1.setText("");
                }
            }
        }
        if(e.getSource() == jbutton4)
        {
            Recibo();
        }
        if(e.getSource() == jbutton5)
        {
            defaultlistmodel.removeAllElements();
        }
        
        if(jcheckbox2.isSelected())
        {
            jpanelnuevo6.setVisible(true);
        }
        else
        {
            jpanelnuevo6.setVisible(false);
        }
        if(e.getSource() == jbutton2)
        {
            int returnVal = jfilechooser.showOpenDialog(MAIN.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) 
            {
            } 
        }
        
        if(e.getSource() == jbutton3)
        {
            JOptionPane.showMessageDialog(null, getINAPAM + getArticulo + getMarca + getCosto + getFarmaceutica);
            try 
            {
                Insertar();
            } 
            catch (Exception ex) 
            {
                Logger.getLogger(MAIN.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    void Insertar() throws Exception
    {
        Connection conn = null;
        Statement sentencia;
        
        getArticulo = jtextfield2.getText();
        getMarca = jcombobox1.getSelectedItem();
        getCosto = jspinner.getValue();
        getFarmaceutica = jcombobox2.getSelectedItem();

        if(jcheckbox1.isSelected())
        {
            getINAPAM = true;
        }
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            sentencia = conn.createStatement();
            
            if (conn != null)
            {
                System.out.println("Conexion a base de datos " + url + " ... Ok");
                sentencia.executeUpdate
                (
                    "INSERT INTO Farmacia VALUES ('','" + getArticulo + "','" + getFarmaceutica + "','" + getMarca + "','" + getCosto + "','','" + getINAPAM + "','  getImagen  ', '')"
                );
                conn.close();
            }
        }
        catch(SQLException ex)
        {
            System.out.println("Hubo un problema al intentar conectarse con la base de datos " + url);
        }
        catch(ClassNotFoundException ex)
        {
            System.out.println(ex);
        }
        catch(Exception e)
        {
            System.out.println(e);
            return;
        }
    }
    
    void Consultar() throws Exception
    {
        Connection conn = null;
        Statement sentencia;
        ResultSet resultSet = null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, login, password);
            sentencia = conn.createStatement();
            
            if (conn != null)
            {
                System.out.println("Conexion a base de datos " + url + " ... Ok");
                resultSet = sentencia.executeQuery
                (
                    "SELECT "
                );
                while(resultSet.next())
                {
                    defaulttablemodel.addRow(new Object[]{resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7)});
                }
                resultSet.close();
                sentencia.close();
                conn.close();
            }
        }
        catch(SQLException ex)
        {
            System.out.println("Hubo un problema al intentar conectarse con la base de datos " + url);
        }
        catch(ClassNotFoundException ex)
        {
            System.out.println(ex);
        }
        catch(Exception e)
        {
            System.out.println(e);
            return;
        }
    }
}