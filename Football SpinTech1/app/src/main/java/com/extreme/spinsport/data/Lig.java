package com.extreme.spinsport.data;

public class Lig {
    String Lig, League, Logo;
    int Pos;
    String show;

    public Lig(){}

    public Lig(String Lig, String League, String Logo, int Pos, String show){
        this.Lig = Lig;
        this.League = League;
        this.Logo = Logo;
        this.Pos = Pos;
        this.show = show;
    }

    public String getLig(){return Lig;}

    public void setLig(String Lig) {
        this.Lig = Lig;
    }

    public String getLeague(){return League;}

    public void setLeague(String League){
        this.League = League;
    }

    public String getLogo(){return Logo;}

    public void setLogo(String Logo) {
        this.Logo = Logo;
    }

    public int getPos() {
        return Pos;
    }

    public void setPos(int pos) {
        this.Pos = pos;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }
}
