package mainFrame;

public enum StationId{
	空(""),南港("0990"),臺北("1000"),板橋("1010"),桃園("1020"),新竹("1030"),苗栗("1035"),台中(""),彰化("1043"),雲林("1047"),嘉義("1050"),台南("1060"),左營("1070");
	private String value;
	private StationId(String id){
		this.value = id;
	}
	public String getValue(){
		return value;
	}
}
