package com.example.workfinder;

public class JobsList {
    private String applyDt, company, duration, jobTitle, location, stipend, type, id;

    public JobsList() {
    }

    public JobsList(String applyDt, String company, String duration, String jobTitle, String location, String stipend, String type, String id)
    {
        this.applyDt=applyDt;
        this.company=company;
        this.duration=duration;
        this.jobTitle=jobTitle;
        this.location=location;
        this.stipend=stipend;
        this.type=type;
        this.id=id;
    }

    public  String getCompany(){return  company;}
    public void setCompany(String company){this.company=company;}

    public  String getApplyDt(){return  applyDt;}
    public void setApplyDt(String applyDt){this.applyDt=applyDt;}

    public  String getDuration(){return  duration;}
    public void setDuration(String duration){this.duration=duration;}

    public  String getJobTitle(){return  jobTitle;}
    public void setJobTitle(String jobTitle){this.jobTitle=jobTitle;}

    public  String getLocation(){return  location;}
    public void setLocation(String location){this.location=location;}

    public  String getStipend(){return  stipend;}
    public void setStipend(String stipend){this.stipend=stipend;}

    public  String getType(){return  type;}
    public void setType(String type){this.type=type;}

    public  String getId(){return  id;}
    public void setId(String stipend){this.id=id;}
}
