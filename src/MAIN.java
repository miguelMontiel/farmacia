import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.Connection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class MAIN extends JFrame implements ActionListener
{    
    String getArticulo, getImage;
    Boolean getINAPAM = false;
    Object getFarmaceutica, getMarca, getCosto;
    String[] nombreColumnas = {"ARTICULO", "FARMACEUTICA", "MARCA", "COSTO", "INVENTARIO", "INAPAM", "IMAGEN"};
    Object[][] celdas = {{"1", "2", "3", "4", "5", "6", "7"}};
    String[] marca = {"1", "2", "3", "4", "5"}; // Cambiar por valores reales
    String[] farmaceutica = {"1", "2", "3", "4", "5"}; // Cambiar por valores reales
    
    BorderLayout borderlayout;
    JTabbedPane tabbedpane;
    JPanel jpanelbuscar, jpanelnuevo, jpanelnuevo1, jpanelnuevo2, jpanelnuevo3, jpanelnuevo4, jpanelnuevo5, jpanelnuevo6, jpanelnuevo7, jpaneltodo;
    DefaultTableModel defaulttablemodel;
    JTable jtable;
    JScrollPane jscrollpane;
    ImageIcon BuscarPNG, ListaPNG, TodosPNG;
    
    JLabel jlabel1, jlabel2, jlabel3;
    JTextField jtextfield1, jtextfield2, jtextfield3;
    JComboBox jcombobox1, jcombobox2;
    JSpinner jspinner;
    JCheckBox jcheckbox1, jcheckbox2;
    JButton jbutton1, jbutton2, jbutton3;
    JFileChooser jfilechooser;
    
    MAIN()
    {
        borderlayout = new BorderLayout();
        tabbedpane = new JTabbedPane();
        jpanelbuscar = new JPanel();
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
        defaulttablemodel = new DefaultTableModel(celdas, nombreColumnas);
        jtable = new JTable(defaulttablemodel);
        jscrollpane = new JScrollPane(jtable);
        jscrollpane.setSize(600, 250);
        BuscarPNG = createImageIcon("Buscar.png");
        ListaPNG = createImageIcon("Lista.png");
        TodosPNG = createImageIcon("Todos.png");
        
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
        jfilechooser = new JFileChooser();
        
        jbutton1.addActionListener(this);
        jbutton2.addActionListener(this);
        jbutton3.addActionListener(this);
        jcheckbox2.addActionListener(this);
        
        setLayout(borderlayout);
        add(tabbedpane);
        
        setIcon();
        Buscar();
        Ingresar();
        Productos();
        lookandFeel();
    }
    
    void Buscar()
    {   
        tabbedpane.addTab("Buscar", BuscarPNG, jpanelbuscar, "Aqui se buscan los productos que se van a comprar.");
        jpanelbuscar.setLayout(new GridLayout(5, 3, 10, 10));
        
        jpanelbuscar.add(new JLabel(" "));
        jpanelbuscar.add(new JLabel("Introduzca el nombre del articulo a buscar: "));
        jpanelbuscar.add(jtextfield1);
	jpanelbuscar.add(jbutton1);
        jpanelbuscar.add(new JLabel(" "));
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
    
    public void actionPerformed(ActionEvent e)
    {
        if(jcheckbox2.isSelected())
        {
            jpanelnuevo6.setVisible(true);
        }
        else
        {
            jpanelnuevo6.setVisible(false);
        }
        if(e.getSource() == jbutton1)
        {
            if(jtextfield1.getText().equals("Gaby"))
            {
                JOptionPane.showMessageDialog(null, "<3");
            }
            else
            {
                JOptionPane.showMessageDialog(null, jtextfield1.getText());
            }
        }
        if(e.getSource() == jbutton2)
        {
            int returnVal = jfilechooser.showOpenDialog(MAIN.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) 
            {
                File file = jfilechooser.getSelectedFile();
                getImage = file.getName();
                jtextfield3.setText(getImage);
            } 
        }
        if(e.getSource() == jbutton3)
        {
            getArticulo = jtextfield2.getText();
            getMarca = jcombobox1.getSelectedItem();
            getCosto = jspinner.getValue();
            getFarmaceutica = jcombobox2.getSelectedItem();
            if(jcheckbox1.isSelected())
            {
                getINAPAM = true;
            }
            JOptionPane.showMessageDialog(null, getINAPAM);
        }
    }
}