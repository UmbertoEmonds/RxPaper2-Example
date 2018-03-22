package umbertoemonds.paper.data.rayon.model;

import java.util.ArrayList;

import umbertoemonds.paper.data.produit.model.Produit;

/**
 * Created by umbertoemonds on 11/03/2018.
 */

public class Rayon {

    private String name;
    private ArrayList<Produit> produits;

    public Rayon(String name, ArrayList<Produit> produits) {
        this.name = name;
        this.produits = produits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Produit> getProduits() {
        return produits;
    }

    public void setProduits(ArrayList<Produit> produits) {
        this.produits = produits;
    }
}
