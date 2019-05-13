package com.example.intellectures.ui.gps

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.intellectures.R
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.annotation.SuppressLint
import android.location.*
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_gps.*
import java.io.IOException
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [GpsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [GpsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class GpsFragment : Fragment() {
    val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

    private var locationListener: LocationListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gps, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GpsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = GpsFragment()
    }

    override fun onResume() {
        super.onResume()
        showGps()
    }

    override fun onPause() {
        stopGps()
        super.onPause()
    }

    private fun showGps() {
        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        } else {
            doShowGps()
        }
    }

    private fun stopGps() {
        val locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        if (locationListener != null) {
            locationManager?.removeUpdates(locationListener)
            locationListener = null
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            doShowGps()
        }
    }

    @SuppressLint("MissingPermission")
    fun doShowGps() {
        val locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        locationListener = MyLocationListener(gps)
        locationManager?.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, 5000L, 10f, locationListener
        )
    }

    private inner class MyLocationListener(val gpsView: TextView): LocationListener {

        override fun onLocationChanged(loc: Location) {
            val longitude = "Longitude: " + loc.getLongitude()
            val latitude = "Latitude: " + loc.getLatitude()

            var cityName: String? = null
            var countryName: String? = null
            val gcd = Geocoder(context, Locale.getDefault())
            val addresses: List<Address>
            try {
                addresses = gcd.getFromLocation(
                    loc.getLatitude(),
                    loc.getLongitude(), 1
                )
                if (addresses.size > 0) {
                    System.out.println(addresses[0].getLocality())
                    cityName = addresses[0].locality
                    countryName = addresses[0].countryName
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            val s = (longitude + "\n" + latitude + "\n\nCity: "
                    + cityName + "\nCountry:" + countryName)
            gpsView.setText(s)
        }

        override fun onProviderDisabled(provider: String) {}

        override fun onProviderEnabled(provider: String) {}

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    }
}
