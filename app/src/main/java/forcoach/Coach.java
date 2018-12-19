package forcoach;

public class Coach {
    private String cname;
    private int imageId;
    private String cintro;

    public Coach(String cname,String cintro,int imageId){
        this.cname=cname;
        this.imageId=imageId;
        this.cintro=cintro;
    }
    public String getName(){
        return cname;
    }
    public int getImageId(){
        return imageId;
    }
    public String getIntro(){ return cintro;}
}
