#include "BitmapAndPixelCount.h"

/**
 * ビットマップ部とピクセル参照カウント
 * @param bitmap ビットマップ部
 * @param pixelCount ピクセル新規参照カウント
 * @param referCount 既存ピクセル参照カウント
 */
BitmapAndPixelCount::BitmapAndPixelCount(unsigned char ** bitmap, int pixelCount, int referCount)
{
	this->bitmap = bitmap;
	this->pixelCount = pixelCount;
	this->referCount = referCount;
}

/**
 * ピクセル新規参照の割合を返却
 * @return ピクセル新規参照の割合
 */
float BitmapAndPixelCount::getPixelCountRatio()
{
	return (float)pixelCount * 100 / (float)(pixelCount + referCount);
}
