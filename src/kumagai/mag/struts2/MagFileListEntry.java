package kumagai.mag.struts2;

/**
 * MAGファイル一覧表示用エントリ
 * @author kumagai
 */
public class MagFileListEntry
{
	public final String filename;
	public final String filenameAndMemo;

	/**
	 * 指定の値をメンバーに割り当てる
	 * @param filename ファイル名
	 * @param memo メモ
	 * @param screenMode スクリーンモード
	 */
	public MagFileListEntry(String filename, String memo, int screenMode)
	{
		this.filename = filename;
		this.filenameAndMemo = String.format("%s %s(mode=%d)", filename, memo, screenMode);
	}
}
