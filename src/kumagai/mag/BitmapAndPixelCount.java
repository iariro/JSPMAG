package kumagai.mag;

/**
 * ビットマップ部とピクセル参照カウント
 * @author kumagai
 */
public class BitmapAndPixelCount
{
	public final byte [][] bitmap;
	public final int pixelCount;
	public final int referCount;

	/**
	 * ビットマップ部とピクセル参照カウント
	 * @param bitmap ビットマップ部
	 * @param pixelCount ピクセル新規参照カウント
	 * @param referCount 既存ピクセル参照カウント
	 */
	BitmapAndPixelCount(byte [][] bitmap, int pixelCount, int referCount)
	{
		this.bitmap = bitmap;
		this.pixelCount = pixelCount;
		this.referCount = referCount;
	}

	/**
	 * ピクセル新規参照の割合を返却
	 * @return ピクセル新規参照の割合
	 */
	public float getPixelCountRatio()
	{
		return (float)pixelCount * 100 / (float)(pixelCount + referCount);
	}
}
