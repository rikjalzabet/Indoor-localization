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
import hr.foi.air.core.models.IZone
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Locale

class PDFReportGenerator : IReportGenerator {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

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

    private fun generateAssetTable(
        asset: IAsset,
        zoneHistories: List<IAssetZoneHistory>,
        positionHistories: List<IAssetPositionHistory>,
    ): List<Paragraph> {
        val paragraphs = mutableListOf<Paragraph>()

        paragraphs.add(Paragraph("Asset Details"))
        paragraphs.add(Paragraph("ID: ${asset.id}"))
        paragraphs.add(Paragraph("Name: ${asset.name}"))
        paragraphs.add(Paragraph("Coordinates: (${asset.x}, ${asset.y})"))
        paragraphs.add(Paragraph("Last Sync: ${formatTimestamp(asset.lastSync)}"))
        paragraphs.add(Paragraph("Floor Map ID: ${asset.floorMapId}"))
        paragraphs.add(Paragraph("Active: ${asset.active}"))
        paragraphs.add(Paragraph("\n"))

        val assetZoneHistories = zoneHistories.filter { it.assetId == asset.id }
        if (assetZoneHistories.isNotEmpty()) {
            paragraphs.add(Paragraph("Zone History"))
            //val zoneTable = Table(UnitValue.createPercentArray(floatArrayOf(25f, 25f, 25f, 25f))).useAllAvailableWidth()
            val zoneTable = Table(4).useAllAvailableWidth()
            zoneTable.addHeaderCell("Zone ID")
            //zoneTable.addHeaderCell("Zone Name")
            zoneTable.addHeaderCell("Enter Date Time")
            zoneTable.addHeaderCell("Exit Date Time")
            zoneTable.addHeaderCell("Retention Time")

            assetZoneHistories.forEach { history ->
                //val zone = zones.find { it.id == history.zoneId }
                zoneTable.addCell(history.zoneId.toString())
                //zoneTable.addCell(zone?.name ?: "-")
                zoneTable.addCell(formatTimestamp(history.enterDateTime))
                zoneTable.addCell(history.exitDateTime?.let { formatTimestamp(it) } ?: "-")
                zoneTable.addCell(history.retentionTime ?: "-")
            }
            paragraphs.add(Paragraph().add(zoneTable))
            paragraphs.add(Paragraph("\n"))
        }

        val assetPositionHistories = positionHistories.filter { it.assetId == asset.id }
        if (assetPositionHistories.isNotEmpty()) {
            paragraphs.add(Paragraph("Position History"))
            //val positionTable = Table(UnitValue.createPercentArray(floatArrayOf(25f, 25f, 25f, 25f))).useAllAvailableWidth()
            val positionTable = Table(4).useAllAvailableWidth()
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
            paragraphs.add(Paragraph().add(positionTable))
            paragraphs.add(Paragraph("\n"))
        }

        paragraphs.add(Paragraph("----------------------------------------"))
        paragraphs.add(Paragraph("\n"))

        return paragraphs
    }

    override fun saveReport(
        assets: List<IAsset>,
        zoneHistories: List<IAssetZoneHistory>,
        positionHistories: List<IAssetPositionHistory>,
        file: File
    ) {
        createPdfDocument(file) { document ->
            assets.forEach { asset ->
                generateAssetTable(asset, zoneHistories, positionHistories).forEach { document.add(it) }
            }
        }
    }
}