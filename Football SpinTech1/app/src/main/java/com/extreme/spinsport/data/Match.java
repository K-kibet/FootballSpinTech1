package com.extreme.spinsport.data;

public class Match {

    String Team1, Team2, Status, Logo1, Logo2, Link, Linke;

    public Match(){}

    public Match(String Team1, String Team2, String Status, String Logo1, String Logo2, String Link, String Linke){
        this.Team1 = Team1;
        this.Team2 = Team2;
        this.Status = Status;
        this.Logo1 = Logo1;
        this.Logo2 = Logo2;
        this.Link = Link;
        this.Linke = Linke;
    }

    public String getTeam1(){return Team1;}

    public void setTeam1(String Team1) {
        this.Team1 = Team1;
    }

    public String getTeam2(){return Team2;}

    public void setTeam2(String Team2){
        this.Team2 = Team2;
    }

    public String getStatus(){return Status;}

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getLogo1(){return Logo1;}

    public void setLogo1(String Logo1) {
        this.Logo1 = Logo1;
    }

    public String getLogo2(){return Logo2;}

    public void setLogo2(String Logo2) {
        this.Logo2 = Logo2;
    }

    public String getLink(){return Link;}

    public void setLink(String Link) {
        this.Link = Link;
    }

    public String getLinke(){return Link;}

    public void setLinke(String Link) {
        this.Link = Link;
    }

}
