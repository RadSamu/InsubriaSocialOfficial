package it.uninsubria.insubriasocial

import android.content.Intent
import android.graphics.Rect
import android.location.GpsStatus
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import it.uninsubria.insubriasocial.databinding.ActivityPaginaMostraMappaBinding
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class PaginaMostraMappa : AppCompatActivity(), MapListener, GpsStatus.Listener {

    lateinit var mMap: MapView
    lateinit var controller: IMapController;
    lateinit var mMyLocationOverlay: MyLocationNewOverlay;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPaginaMostraMappaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Configuration.getInstance().load(
            applicationContext,
            getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        )
        val currentUser = intent.getStringExtra("currentUser")
        val annuncio = intent.getStringExtra("annuncio")
        findViewById<Button>(R.id.btnIndietro3).setOnClickListener {
            val tornaIndietro = Intent(this, PaginaMostraAnnuncio::class.java)
                .putExtra("currentUser", currentUser)
                .putExtra("annuncio", annuncio)
            startActivity(tornaIndietro)
        }
        mMap = binding.osmmap
        mMap.setTileSource(TileSourceFactory.MAPNIK)
        mMap.mapCenter
        mMap.setMultiTouchControls(true)
        mMap.getLocalVisibleRect(Rect())
        mMyLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), mMap)
        mMap.overlays.add(mMyLocationOverlay)
        controller = mMap.controller



        mMyLocationOverlay.enableMyLocation()
        mMyLocationOverlay.enableFollowLocation()
        mMyLocationOverlay.isDrawAccuracyEnabled = true
        mMyLocationOverlay.runOnFirstFix {
            runOnUiThread {
                controller.setCenter(mMyLocationOverlay.myLocation);
                controller.animateTo(mMyLocationOverlay.myLocation)
            }
        }


        val posizione = intent.getStringExtra("posizione")
        var latitude = 0.0
        var longitude = 0.0
        when(posizione){
            "Pad. Seppilli" -> {
                latitude = 45.79937610926918
                longitude = 8.847147783127179
            }
            "Pad. Morselli" -> {
                latitude = 45.79893950967056
                longitude = 8.849153369634463
            }
            "Pad. Antonini" -> {
                 latitude = 45.799073288839836
                 longitude = 8.850189011962948
            }
            "Pad. Monte Generoso" -> {
                 latitude = 45.79866297013574
                 longitude = 8.853074371484748
            }
            "Mensa Monte Generoso" -> {
                 latitude = 45.79857861591073
                 longitude = 8.853087478114666
            }
        }
        val mapPoint = GeoPoint(latitude, longitude)

        controller.setCenter(mapPoint)
        controller.setZoom(20.0)

        mMap.addMapListener(this)
        controller.animateTo(mapPoint)
        mMap.overlays.add(mMyLocationOverlay)
        mMap.addMapListener(this)

        Log.e("TAG", "onCreate:in ${controller.zoomIn()}")
        Log.e("TAG", "onCreate: out  ${controller.zoomOut()}")




    }
// metodi ulteriori
    override fun onScroll(event: ScrollEvent?): Boolean {
        // event?.source?.getMapCenter()
        Log.e("TAG", "onCreate:la ${event?.source?.getMapCenter()?.latitude}")
        Log.e("TAG", "onCreate:lo ${event?.source?.getMapCenter()?.longitude}")
        //  Log.e("TAG", "onScroll   x: ${event?.x}  y: ${event?.y}", )
        return true
    }

    override fun onZoom(event: ZoomEvent?): Boolean {
        //  event?.zoomLevel?.let { controller.setZoom(it) }


        Log.e("TAG", "onZoom zoom level: ${event?.zoomLevel}   source:  ${event?.source}")
        return false;
    }

    override fun onGpsStatusChanged(event: Int) {


        TODO("Not yet implemented")
    }


}