public class Pessoa {
    String nome;
    Double latitude,longitude;
    String profissao;
    Boolean disponibilidade;

    public Pessoa(String nome, Double latitude, Double longitude, String profissao, Boolean disponibilidade) {

        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
        this.profissao = profissao;
        this.disponibilidade = disponibilidade;
    }

    //getters and setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public Boolean getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(Boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }


}
