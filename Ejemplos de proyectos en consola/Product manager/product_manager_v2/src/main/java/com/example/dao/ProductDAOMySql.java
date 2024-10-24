package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.entidades.Product;

public class ProductDAOMySql implements ProductDAO {
    private static final String db = "jdbc:mariadb://localhost:3306/product-manager";
    private static final String user = "root";
    private static final String pass = "";

    @Override
    public List<Product> getProductos() {
        List<Product> Productos = new ArrayList<Product>();

        try (Connection conn = DriverManager.getConnection(db, user, pass)) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from product");

            while(rs.next()) {
                // Product(int id, String referencia, String nombre, double precio, int categoria) {
                Productos.add(new Product(rs.getInt("id"), 
                                          rs.getString("reference"),
                                          rs.getString("name"),
                                          rs.getDouble("price"),
                                          rs.getInt("category")));
            }
        } 
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
        return Productos;
    }

    @Override
    public List<Product> getProdutosCategoria(int idCat) {
        List<Product> Productos = new ArrayList<Product>();

        try (Connection conn = DriverManager.getConnection(db, user, pass)) {
            PreparedStatement st = conn.prepareStatement("select * from product where category = ?");
            st.setInt(1, idCat);
            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                // Product(int id, String referencia, String nombre, double precio, int categoria) {
                Productos.add(new Product(rs.getInt("id"), 
                                          rs.getString("reference"),
                                          rs.getString("name"),
                                          rs.getDouble("price"),
                                          rs.getInt("category")));
            }
        } 
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
        return Productos;
    }

    @Override
    public void insertProducto(Product Producto) {
        try (Connection conn = DriverManager.getConnection(db, user, pass)) {
            PreparedStatement st = conn.prepareStatement("insert into product(reference,name,price,category) values(?,?,?,?)");
            st.setString(1, Producto.getReferencia());
            st.setString(2, Producto.getNombre());
            st.setDouble(3, Producto.getPrecio());
            st.setInt(4, Producto.getCategoria());
            st.executeUpdate();
        } 
        catch (SQLException e) {
            System.err.println(e.getMessage());
        } 
    }

    @Override
    public void deleteProducto(int id) {
        try (Connection conn = DriverManager.getConnection(db, user, pass)) {
            PreparedStatement st = conn.prepareStatement("delete from product where id = ?");
            st.setInt(1, id);
            st.executeUpdate();
        } 
        catch (SQLException e) {
            System.err.println(e.getMessage());
        } 
    }

    @Override
    public void updateProducto(Product Producto) {
        try (Connection conn = DriverManager.getConnection(db, user, pass)) {
            PreparedStatement st = conn.prepareStatement("update product set reference = ?, name = ?, price = ?, category = ? where id = ?");
            st.setString(1, Producto.getReferencia());
            st.setString(2, Producto.getNombre());
            st.setDouble(3, Producto.getPrecio());
            st.setInt(4, Producto.getCategoria());
            st.setInt(5, Producto.getId());
            st.executeUpdate();
        } 
        catch (SQLException e) {
            System.err.println(e.getMessage());
        } 
    }
}
