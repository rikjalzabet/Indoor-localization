package hr.foi.air.save_object_info

import android.util.Log
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.UnitValue
import hr.foi.air.core.models.IAsset
import hr.foi.air.core.models.IAssetPositionHistory
import hr.foi.air.core.models.IAssetZoneHistory
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Locale

class PDFReportGenerator() {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS", Locale.US)

    private inline fun <T> createPdfDocument(file: File, block: (Document) -> T): T {
        val pdfWriter = PdfWriter(FileOutputStream(file))
        val pdfDocument = PdfDocument(pdfWriter)
        val document = Document(pdfDocument)

        try {
            return block(document)
        } finally {
            document.close()
            pdfDocument.close()
            pdfWriter.close()
        }
    }

    private fun formatTimestamp(timestamp: String): String {
        return try {
            val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.US)
            val date = isoFormat.parse(timestamp)
            dateFormat.format(date)
        } catch (e: Exception) {
            Log.e("PDFReportGenerator", "Error formatting timestamp: ${e.message}")
            timestamp
        }
    }

    fun saveReport(
        assets: List<IAsset>,
        zoneHistories: List<IAssetZoneHistory>,
        positionHistories: List<IAssetPositionHistory>,
        file: File
    ) {
        createPdfDocument(file) { document ->
            assets.forEach { asset ->
                document.add(Paragraph("Asset Details"))
                document.add(Paragraph("ID: ${asset.id}"))
                document.add(Paragraph("Name: ${asset.name}"))
                document.add(Paragraph("Coordinates: (${asset.x}, ${asset.y})"))
                document.add(Paragraph("Last Sync: ${formatTimestamp(asset.lastSync)}"))
                document.add(Paragraph("Floor Map ID: ${asset.floorMapId}"))
                document.add(Paragraph("Active: ${asset.active}"))
                document.add(Paragraph("\n"))

                val assetZoneHistories = zoneHistories.filter { it.assetId == asset.id }
                if (assetZoneHistories.isNotEmpty()) {
                    document.add(Paragraph("Zone History"))
                    val zoneTable = Table(UnitValue.createPercentArray(floatArrayOf(2f, 2f, 2f, 2f))).useAllAvailableWidth()
                    zoneTable.addHeaderCell("Zone")
                    zoneTable.addHeaderCell("Enter Date Time")
                    zoneTable.addHeaderCell("Exit Date Time")
                    zoneTable.addHeaderCell("Retention Time")

                    assetZoneHistories.forEach { history ->
                        zoneTable.addCell(history.zoneId.toString())
                        zoneTable.addCell(formatTimestamp(history.enterDateTime))
                        zoneTable.addCell(history.exitDateTime?.let { formatTimestamp(it) } ?: "-")
                        zoneTable.addCell(history.retentionTime.toString())
                    }
                    document.add(zoneTable)
                    document.add(Paragraph("\n"))
                }

                val assetPositionHistories = positionHistories.filter { it.assetId == asset.id }
                if (assetPositionHistories.isNotEmpty()) {
                    document.add(Paragraph("Position History"))
                    val positionTable = Table(UnitValue.createPercentArray(floatArrayOf(2f, 2f, 2f, 2f))).useAllAvailableWidth()
                    positionTable.addHeaderCell("X")
                    positionTable.addHeaderCell("Y")
                    positionTable.addHeaderCell("Date Time")
                    positionTable.addHeaderCell("Floor Map ID")

                    assetPositionHistories.forEach { position ->
                        positionTable.addCell(position.x.toString())
                        positionTable.addCell(position.y.toString())
                        positionTable.addCell(formatTimestamp(position.dateTime))
                        positionTable.addCell(position.floorMapId.toString())
                    }
                    document.add(positionTable)
                    document.add(Paragraph("\n"))
                }

                document.add(Paragraph("----------------------------------------"))
                document.add(Paragraph("\n"))
            }
        }
    }
}