/*
 * Copyright 2017 Google Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arcore.example.core.rendering;

import com.google.ar.core.Anchor;
import com.google.ar.core.Plane;
import com.google.ar.core.PointCloud;
import com.google.ar.core.Pose;

/**
 * This class tracks the attachment of object's Anchor to a Plane. It will construct a pose
 * that will stay on the plane (in Y direction), while still properly tracking the XZ changes
 * from the anchor updates.
 */
public class CloudAttachment {
    private final Pose mPose;
    private final PointCloud mPointCloud;
    private final Anchor mAnchor;

    // Allocate temporary storage to avoid multiple allocations per frame.
    private final float[] mPoseTranslation = new float[3];
    private final float[] mPoseRotation = new float[4];

    public CloudAttachment(Pose pose, PointCloud pointCloud, Anchor anchor) {
        mPose = pose;
        mPointCloud = pointCloud;
        mAnchor = anchor;
    }

    public boolean isTracking() {
        return /*true if*/
              mAnchor.getTrackingState() == Anchor.TrackingState.TRACKING;
    }

    public Pose getPose() {
        Pose pose = mAnchor.getPose();
        pose.getTranslation(mPoseTranslation, 0);
        pose.getRotationQuaternion(mPoseRotation, 0);
        mPoseTranslation[1] = mPose.ty();
        return new Pose(mPoseTranslation, mPoseRotation);
    }

    public Anchor getAnchor() {
        return mAnchor;
    }
}
