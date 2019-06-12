package sample;

import javafx.beans.property.StringProperty;

public class ComplaintRequest {
    private StringProperty content;
    private StringProperty subject;
    private StringProperty owner;
    private StringProperty isApproved;

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public String getID() {
        return ID.get();
    }

    public StringProperty IDProperty() {
        return ID;
    }

    private StringProperty ID;


    public ComplaintRequest(StringProperty content, StringProperty subject, StringProperty owner, StringProperty isApproved, StringProperty ID) {
        this.content = content;
        this.subject = subject;
        this.owner = owner;
        this.isApproved = isApproved;
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "complaintRequest{" +
                "content=" + content +
                ", subject=" + subject +
                ", owner=" + owner +
                ", isApproved=" + isApproved +
                ", ID="+ ID +'}';
    }
    public void setContent(String content) {
        this.content.set(content);
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }

    public void setOwner(String owner) {
        this.owner.set(owner);
    }

    public void setIsConnection(String isConnection) {
        this.isApproved.set(isConnection);
    }

    public String getContent() {
        return content.get();
    }

    public StringProperty contentProperty() {
        return content;
    }

    public String getSubject() {
        return subject.get();
    }

    public StringProperty subjectProperty() {
        return subject;
    }

    public String getOwner() {
        return owner.get();
    }

    public StringProperty ownerProperty() {
        return owner;
    }

    public String getIsApproved() {
        return isApproved.get();
    }

    public StringProperty TradeableProperty() {
        return isApproved;
    }
}
