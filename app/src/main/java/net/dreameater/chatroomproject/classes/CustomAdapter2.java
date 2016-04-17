package net.dreameater.chatroomproject.classes;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.dreameater.chatroomproject.R;

import java.util.List;

public class CustomAdapter2 extends ArrayAdapter<Message> {
    private String mUserId;

    public CustomAdapter2(Context context, String userId, List<Message> messages) {
        super(context, 0, messages);
        this.mUserId = userId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.customlist, parent, false);
            ViewHolder holder = new ViewHolder();
            //holder.imageOther = (ImageView)convertView.findViewById(R.id.ivProfileOther);
            //holder.imageMe = (ImageView)convertView.findViewById(R.id.ivProfileMe);
            holder.body = (TextView)convertView.findViewById(R.id.RoomItem);
            convertView.setTag(holder);
        }
        final Message message = getItem(position);
        final ViewHolder holder = (ViewHolder)convertView.getTag();
        final boolean isMe = message.getUserId().equals(mUserId);
        // Show-hide image based on the logged-in user.
        // Display the profile image to the right for our user, left for other users.
        if (isMe) {
            //holder.imageMe.setVisibility(View.VISIBLE);
            //holder.imageOther.setVisibility(View.GONE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        } else {
            //holder.imageOther.setVisibility(View.VISIBLE);
            //holder.imageMe.setVisibility(View.GONE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        }
        holder.body.setText(message.getBody());
        return convertView;
    }

    // Create a gravatar image based on the hash value obtained from userId
    /*private static String getProfileUrl(final String userId) {
        String hex = "";
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] hash = digest.digest(userId.getBytes());
            final BigInteger bigInt = new BigInteger(hash);
            hex = bigInt.abs().toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "http://www.gravatar.com/avatar/" + hex + "?d=identicon";
    }*/

    final class ViewHolder {
        public ImageView imageOther;
        public ImageView imageMe;
        public TextView body;
    }
}